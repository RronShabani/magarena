package magic.card;

import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPowerToughness;
import magic.model.MagicLayer;
import magic.model.mstatic.MagicStatic;
import magic.model.target.MagicTargetFilter;

public class Armament_Master {
    public static final MagicStatic S = new MagicStatic(
        MagicLayer.ModPT, 
	    MagicTargetFilter.TARGET_KOR_YOU_CONTROL) {
    	
    	private int amount = 0;
    	
    	@Override
		public void getPowerToughness(final MagicGame game,final MagicPermanent permanent,final MagicPowerToughness pt) {
				pt.add(amount, amount);
		}
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
        	if (source.isEquipped()) {
        		amount = 2 * source.getEquipmentPermanents().size();
        	}
            return source != target;
        }
    };
}
