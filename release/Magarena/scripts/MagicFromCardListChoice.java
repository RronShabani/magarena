package magic.model.choice;

import magic.model.MagicCard;
import magic.model.MagicCardList;
import magic.model.MagicGame;
import magic.model.MagicPlayer;
import magic.model.MagicSource;
import magic.model.event.MagicEvent;
import magic.ui.GameController;
import magic.ui.UndoClickedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MagicFromCardListChoice extends MagicChoice {

    private static final String MESSAGE="Choose a card.";
    private final MagicCardList cardList;
    private final int amount;

    public MagicFromCardListChoice(final MagicCardList cardList,final int amount) {
        super(genDescription(amount));
        this.cardList=cardList;
        this.amount=amount;
    }

    private static final String genDescription(final int amount) {
        if (amount==1) {
            return "Choose a card.";
        } else {
            return "Choose " + amount + " cards.";
        }
    }

    private void createOptions(
            final Collection<Object> options,
            final MagicCardList cList,
            final MagicCard[] cards,
            final int count,
            final int aAmount,
            final int index) {

        if (count == aAmount) {
            options.add(new MagicCardChoiceResult(cards));
            return;
        }

        final int left = cList.size() - index;
        if (count + left < aAmount) {
            return;
        }

        cards[count]=cList.get(index);
        createOptions(options,cList,cards,count+1,aAmount,index+1);
        createOptions(options,cList,cards,count,aAmount,index+1);
    }

    @Override
    Collection<Object> getArtificialOptions(
            final MagicGame game,
            final MagicEvent event,
            final MagicPlayer player,
            final MagicSource source) {

        final List<Object> options = new ArrayList<Object>();
        final MagicCardList cList = new MagicCardList(this.cardList);
        Collections.sort(cList);
        final int actualAmount = Math.min(amount,cList.size());
        if (actualAmount > 0) {
            createOptions(options,cList,new MagicCard[actualAmount],0,actualAmount,0);
        } else {
            options.add(new MagicCardChoiceResult());
        }
        return options;
    }

    @Override
    public Object[] getPlayerChoiceResults(
            final GameController controller,
            final MagicGame game,
            final MagicPlayer player,
            final MagicSource source) throws UndoClickedException {

        final MagicCardChoiceResult result=new MagicCardChoiceResult();
        final Set<Object> validCards=new HashSet<Object>(this.cardList);
        int actualAmount=Math.min(amount,validCards.size());
        for (;actualAmount>0;actualAmount--) {
            final String message=result.size()>0?result.toString()+"|"+MESSAGE:MESSAGE;
            controller.showCards(this.cardList);
            controller.focusViewers(5,-1);
            controller.disableActionButton(false);
            controller.setValidChoices(validCards,false);
            controller.showMessage(source,message);
            controller.waitForInput();
            final MagicCard card = controller.getChoiceClicked();
            validCards.remove(card);
            result.add(card);
        }
        controller.focusViewers(0,-1);
        return new Object[]{result};
    }
}