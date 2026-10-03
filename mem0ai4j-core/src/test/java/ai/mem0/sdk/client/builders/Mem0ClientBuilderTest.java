package ai.mem0.sdk.client.builders;


import ai.mem0.sdk.filter.MemoryFilter;
import ai.mem0.sdk.internal.Mem0ObjectMapperFactory;
import ai.mem0.sdk.model.memory.SearchMemoryRequest;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Mem0ClientBuilderTest {

    @Test
    void requiresApiKey(){
        assertThrows(RuntimeException.class, () -> Mem0Clients.builder().build());
    }

    @Test
    void configuresProvidedObjectMapperWithMem0SerializationRules() throws Exception {
        ObjectMapper mapper = Mem0ObjectMapperFactory.configure(new ObjectMapper());

        String json = mapper.writeValueAsString(SearchMemoryRequest.builder()
                        .query("What do your remember about me?")
                        .filters(MemoryFilter.eq("user_id", "alice"))
                        .topK(3)
                        .build());
        System.out.println(json);
        assertTrue(json.contains("\"filters\":{\"user_id\":\"alice\"}"));
        assertTrue(json.contains("\"top_k\":3"));
    }

}
