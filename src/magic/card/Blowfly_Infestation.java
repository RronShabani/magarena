package magic.card;

import magic.model.MagicCounterType;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.action.MagicChangeCountersAction;
import magic.model.action.MagicPermanentAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.target.MagicWeakenTargetPicker;
import magic.model.trigger.MagicWhenOtherPutIntoGraveyardFromPlayTrigger;

public class Blowfly_Infestation {
    public static final MagicWhenOtherPutIntoGraveyardFromPlayTrigger T = new MagicWhenOtherPutIntoGraveyardFromPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            return (otherPermanent.isCreature() &&
                    otherPermanent.getCounters(MagicCounterType.MinusOne) > 0) ?
                new MagicEvent(
                    permanent,
                    MagicTargetChoice.TARGET_CREATURE,
                    new MagicWeakenTargetPicker(1,1),
                    this,
                    "Put a -1/-1 counter on target creature$."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            event.processTargetPermanent(game,new MagicPermanentAction() {
                public void doAction(final MagicPermanent creature) {
                    game.doAction(new MagicChangeCountersAction(creature,MagicCounterType.MinusOne,1,true));
                }
            });
        }
    };
}
