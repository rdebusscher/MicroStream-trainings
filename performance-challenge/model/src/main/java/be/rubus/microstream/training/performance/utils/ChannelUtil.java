package be.rubus.microstream.training.performance.utils;

public final class ChannelUtil {

    private ChannelUtil() {
    }

    public static int channelCount() {
        int channels = 1; // basic
        // When you want to get the maximum out of your machine
        channels = Integer.highestOneBit(Runtime.getRuntime().availableProcessors() - 1);
        return channels;
    }
}
