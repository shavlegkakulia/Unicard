package ge.unicard.pos.bus;

import ge.unicard.pos.networking.messaging.base.RewardResponse;

public class MessageReward {

    public final RewardResponse rewardResponse;

    public MessageReward(RewardResponse rewardResponse) {
        this.rewardResponse = rewardResponse;
    }
}
