package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.model.Friendship;
import com.mt.mtSocialMedia.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship,Long> {

    Boolean existsByUserAndFriend(UserEntity user,UserEntity friend);



    Friendship findByUserAndFriend(UserEntity user,UserEntity friend);

    List<Friendship> findAllByUser(UserEntity user);

    List<Friendship> findAllByFriend(UserEntity friend);
}
