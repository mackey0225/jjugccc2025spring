package com.mackey0225.jjugccc2025spring;

import com.google.common.collect.Streams;
import com.mackey0225.jjugccc2025spring.proto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.autoconfigure.client.ConditionalOnGrpcClientEnabled;
import org.springframework.grpc.client.ImportGrpcClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@SpringBootApplication
public class Jjugccc2025springApplication {

    public static void main(String[] args) {
        SpringApplication.run(Jjugccc2025springApplication.class, args);
    }

}

@RestController
class JjugcccController {

    private final JJUGCCCServiceGrpc.JJUGCCCServiceBlockingStub stub;

    public JjugcccController(JJUGCCCServiceGrpc.JJUGCCCServiceBlockingStub stub){
        this.stub = stub;
    }

    @GetMapping("/welcome")
    public Message welcome(@RequestParam String name) {
        WelcomeResponse response = stub.welcome(WelcomeRequest.newBuilder().setName(name).build());
        return new Message(response.getMessage());
    }

    @GetMapping("/recommend")
    public List<Message> recommendTalk(@RequestParam String name, @RequestParam int number) {
        Iterator<RecommendResponse> responses = stub.recommendTalks(
                RecommendRequest.newBuilder().setName(name).setNumber(number).build()
        );
        return Streams.stream(responses).map(r -> new Message(r.getMessage())).toList();
    }
}

record Message(String content) { }
