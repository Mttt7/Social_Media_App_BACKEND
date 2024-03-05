package com.mt.mtSocialMedia.service.serviceImpl;

import com.mt.mtSocialMedia.dto.User.UserRequestDto;
import com.mt.mtSocialMedia.dto.User.UserResponseDto;
import com.mt.mtSocialMedia.mapper.StringResponseMapper;
import com.mt.mtSocialMedia.mapper.UserMapper;
import com.mt.mtSocialMedia.model.Friendship;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.repository.FriendshipRepository;
import com.mt.mtSocialMedia.repository.UserRepository;
import com.mt.mtSocialMedia.service.NotificationService;
import com.mt.mtSocialMedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final NotificationService notificationService;
    @Override
    public Long getUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        return user.getId();
    }

    @Override
    public UserResponseDto getUserProfileById(Long userId) {
        return UserMapper.mapToUserResponseDto(userRepository.findById(userId).orElse(null));
    }

    @Override
    public HashMap<String, String> sendFriendRequest(Long friendId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        UserEntity friend = userRepository.findById(friendId).orElse(null);

        if(friendshipRepository.existsByUserAndFriend(user,friend)){
            return StringResponseMapper.mapToMap("Friend request already sent, or friendship exists");
        }

        Friendship friendship = Friendship.builder()
                .user(user)
                .friend(friend)
                .build();

        friendshipRepository.save(friendship);

        if(friendshipRepository.existsByUserAndFriend(friend,user)){
            notificationService.createNotification(user,friend,"acceptedFriendRequest",user.getId());
        }else{
            notificationService.createNotification(user,friend,"sentFriendRequest",user.getId());
        }


        return StringResponseMapper.mapToMap("Friend Request Sent");
    }

    @Override
    public HashMap<String, String> removeFriend(Long friendId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        UserEntity friend = userRepository.findById(friendId).orElse(null);

        if(friendshipRepository.existsByUserAndFriend(user,friend)){
            Friendship friendship = friendshipRepository.findByUserAndFriend(user,friend);
            Friendship friendshipReverse = friendshipRepository.findByUserAndFriend(friend,user);

            friendshipRepository.delete(friendship);
            friendshipRepository.delete(friendshipReverse);

            return StringResponseMapper.mapToMap("Friendship with user -"+friend.getUsername()+" has been removed");
        } else if (friendshipRepository.existsByUserAndFriend(friend,user)) {
            Friendship friendship = friendshipRepository.findByUserAndFriend(friend,user);
            friendshipRepository.delete(friendship);
            return StringResponseMapper.mapToMap("Friend Request Rejected");
        }
        return StringResponseMapper.mapToMap("Friendship does not exist!");
    }

    @Override
    public HashMap<String, String> getStatusWithGivenUser(Long userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        UserEntity potentialFriend = userRepository.findById(userId).orElse(null);


        if(friendshipRepository.existsByUserAndFriend(user,potentialFriend)){
            if(friendshipRepository.existsByUserAndFriend(potentialFriend,user)){
                return StringResponseMapper.mapToMap("FRIEND");
            }
            else{
                return StringResponseMapper.mapToMap("SENT");
            }
        }else{
            if(friendshipRepository.existsByUserAndFriend(potentialFriend,user)){
                return StringResponseMapper.mapToMap("RECEIVED");
            }
            else{
                return StringResponseMapper.mapToMap("STRANGER");
            }
        }
    }

    @Override
    public UserResponseDto editUserProfile(UserRequestDto userRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        if(userRequestDto.getFirstName() != null) user.setFirstName(userRequestDto.getFirstName());
        if(userRequestDto.getLastName() != null)user.setLastName(userRequestDto.getLastName());
        if(userRequestDto.getEmail() != null) user.setEmail(userRequestDto.getEmail());
        if(userRequestDto.getPhone() != null) user.setEmail(userRequestDto.getPhone());
        if(userRequestDto.getPhotoUrl() != null) user.setPhotoUrl(userRequestDto.getPhotoUrl());
        if(userRequestDto.getBackgroundUrl() != null) user.setBackgroundUrl(userRequestDto.getBackgroundUrl());
        if(userRequestDto.getAbout() != null) user.setAbout(userRequestDto.getAbout());

        return UserMapper.mapToUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> searchFriendsOfUser(Long userId, String query) {

        List<UserResponseDto> friends = this.getFriendList(userId);
        if(query.isEmpty()) return friends;

        return friends.stream()
                .filter(friend -> friend.getFirstName().toLowerCase().contains(query.toLowerCase())
                || friend.getLastName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public Boolean checkUsernameAvailability(String username) {
       return !userRepository.existsByUsername(username);
    }


    @Override
    public List<UserResponseDto> getFriendList(Long userId) {
       UserEntity user = userRepository.findById(userId).orElse(null);
       List<UserResponseDto> friends = new ArrayList<>();

        for (Friendship f :
                friendshipRepository.findAllByUser(user)) {
            if(friendshipRepository.existsByUserAndFriend(f.getFriend(),user)){
                if(friendshipRepository.existsByUserAndFriend(user,f.getFriend())){
                    friends.add(UserMapper.mapToUserResponseDto(f.getFriend()));
                }
            }
        }


        return friends;
    }

    @Override
    public List<UserResponseDto> getSentFriendRequest() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        List<UserResponseDto> sentRequests = new ArrayList<>();

        for (Friendship f:
                friendshipRepository.findAllByUser(user)) {
            if(!friendshipRepository.existsByUserAndFriend(f.getFriend(),user)){
                sentRequests.add(UserMapper.mapToUserResponseDto(f.getFriend()));
            }
        }

        return sentRequests;
    }

    @Override
    public List<UserResponseDto> getReceivedFriendRequest() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

        List<UserResponseDto> receivedRequests = new ArrayList<>();

        for (Friendship f:
                friendshipRepository.findAllByFriend(user)) {
            if(!friendshipRepository.existsByUserAndFriend(user,f.getFriend())){
                receivedRequests.add(UserMapper.mapToUserResponseDto(f.getFriend()));
            }
        }

        return receivedRequests;
    }


}
