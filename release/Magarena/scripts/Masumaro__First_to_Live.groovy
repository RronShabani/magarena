[
    new MagicCDA() {
        @Override
        public void modPowerToughness(final MagicGame game,final MagicPlayer player,final MagicPowerToughness pt) {
            final int amount = player.getHandSize() * 2;
            pt.set(amount,amount);
        }
    }
]
