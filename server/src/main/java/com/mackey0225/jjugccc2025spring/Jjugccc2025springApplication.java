package com.mackey0225.jjugccc2025spring;


import com.mackey0225.jjugccc2025spring.proto.*;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Jjugccc2025springApplication {

    public static void main(String[] args) {
        SpringApplication.run(Jjugccc2025springApplication.class, args);
    }

}


@Service
class JjugcccService extends JJUGCCCServiceGrpc.JJUGCCCServiceImplBase {

    private final Logger log = LoggerFactory.getLogger(JjugcccService.class);

    @Override
    public void welcome(WelcomeRequest request, StreamObserver<WelcomeResponse> responseObserver) {
        log.info("welcome");

        WelcomeResponse response = WelcomeResponse.newBuilder()
                .setMessage(String.format("Hello %s! Welcome to JJUG CCC 2025 Spring!", request.getName()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void recommendTalks(RecommendRequest request, StreamObserver<RecommendResponse> responseObserver) {
        log.info("recommendTalk");
        for (int i = 0; i < request.getNumber(); i++) {
            var message = JjugcccService.pickRandomRoom();
            RecommendResponse response = RecommendResponse.newBuilder()
                    .setMessage(String.format("[%05d] %s さん!  %s !", i, request.getName(), message))
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }



    private static final List<String> MESSAGE_LIST = List.of(
            "Room A+B が良さそう",
            "Room C+D は気になる",
            "Room E は面白そう",
            "Room F もいい感じ",
            "Room G+H はなんだか惹かれますね",
            "Room I は勉強になるかも",
            "Room J も捨てがたいですね",
            "Room L ここがいいかもしれません"
    );

    /**
     * 定義されたルームリストからランダムに一つのルーム名を取得します。
     * @return ランダムに選ばれたルーム名 (String)
     */
    public static String pickRandomRoom() {
        int randomIndex = ThreadLocalRandom.current().nextInt(MESSAGE_LIST.size());

        // 取得したランダムなインデックスを使って、リストから要素を返します。
        return MESSAGE_LIST.get(randomIndex);
    }
}