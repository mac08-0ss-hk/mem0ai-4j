package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FeedbackRequest(String memoryId, String feedback,
                              String feedbackReason) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String memoryId;
        private String feedback;
        private String feedbackReason;

        public Builder memoryId(String memoryId) {
            this.memoryId = memoryId;
            return this;
        }

        public Builder feedback(String feedback) {
            this.feedback = feedback;
            return this;
        }
        public Builder feedbackReason(String feedbackReason) {
            this.feedbackReason = feedbackReason;
            return this;
        }
        public FeedbackRequest build() {
            return new FeedbackRequest(memoryId, feedback, feedbackReason);
        }
    }
}
