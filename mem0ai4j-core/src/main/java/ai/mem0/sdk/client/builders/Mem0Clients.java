package ai.mem0.sdk.client.builders;

import ai.mem0.sdk.client.Mem0Client;

public final class Mem0Clients {
    private Mem0Clients() {}

    public static Mem0ClientBuilder builder(){
        return new Mem0ClientBuilder();
    }

    public static Mem0Client create(String apiKey){
        return builder().apiKey(apiKey).build();
    }
}
