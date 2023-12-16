package fr.univ_lyon1.info.m1.elizagpt.model;

/**
 * Builder class for ChainClient.
 */
public class ChainClientBuilder {
    private Handler chain;
    private MessageProcessor messageProcessor;

    /**
     * Constructs a ChainClientBuilder with the specified MessageProcessor.
     *
     * @param messageProcessor The MessageProcessor to be used by the ChainClient.
     */
    public ChainClientBuilder(final MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }


    /**
     * Adds a handler to the chain.
     *
     * @param handler The handler to be added to the chain.
     * @return The ChainClientBuilder instance for method chaining.
     */
    public ChainClientBuilder addHandler(final Handler handler) {
        if (chain == null) {
            chain = handler;
        } else {
            Handler current = chain;
            while (current.getNextHandler() != null) {
                current = current.getNextHandler();
            }
            current.setNextHandler(handler);
        }
        return this;
    }

    /**
     * Builds and returns a ChainClient instance with the configured chain and MessageProcessor.
     *
     * @return A ChainClient instance.
     */
    public ChainClient build() {
        return new ChainClient(messageProcessor, chain);
    }
}
