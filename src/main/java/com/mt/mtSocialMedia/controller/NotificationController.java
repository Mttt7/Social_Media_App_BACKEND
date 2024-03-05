package com.mt.mtSocialMedia.controller;

import com.mt.mtSocialMedia.dto.NotificationResponseDto;
import com.mt.mtSocialMedia.model.Notification;
import com.mt.mtSocialMedia.service.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<Page<NotificationResponseDto>> getUserNotificationsPaginate(@RequestParam int pageSize,
                                                                                      @RequestParam int pageNumber){
        Page<NotificationResponseDto> res =  notificationService.getUserNotificationsPaginate(pageSize,pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDto> markNotificationAsRead(@PathVariable Long notificationId){
        NotificationResponseDto res = notificationService.markNotificationAsRead(notificationId);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/countUnread")
    public ResponseEntity<Map<String,Integer>> countUnreadNotifications(){
        Integer count = notificationService.countUnreadNotifications();
        Map<String,Integer> res = new HashMap<>();
        res.put("count",count);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
