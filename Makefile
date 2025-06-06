clean:
	./gradlew clean

build:
	@make server-build client-build

server-build:
	./gradlew :server:build

client-build:
	./gradlew :client:build

server-run:
	./gradlew :server:bootRun

client-run:
	./gradlew :client:bootRun

request-to-grpc-client-welcome:
	curl -s "http://localhost:8080/welcome?name=jjugccc" | jq .

request-to-grpc-client-recommend:
	curl -s "http://localhost:8080/recommend?name=jjugccc&number=5" | jq .

request-to-grpc-server-welcome:
	grpcurl -d '{"name": "jjug"}' --plaintext localhost:8081 com.mackey0225.jjugccc2025spring.JJUGCCCService/Welcome

request-to-grpc-server-recommend:
	grpcurl -d '{"name": "jjug", "number":20}' --plaintext localhost:8081 com.mackey0225.jjugccc2025spring.JJUGCCCService/RecommendTalks

request-to-grpc-server-describe:
	grpcurl --plaintext localhost:8081 describe

native-build-server:
	./gradlew :server:nativeCompile

native-build-client:
	./gradlew :client:nativeCompile

run-native-build-server:
	cd ./server/build/native/nativeCompile
	./server

run-native-build-client:
	cd ./client/build/native/nativeCompile
	./client

