package com.mt.mtSocialMedia.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class StringResponseMapper {
    public static HashMap<String,String> mapToMap(String message){
        HashMap<String,String> mapResponse = new HashMap<>();
        mapResponse.put("message",message);
        return mapResponse;
    }
}
