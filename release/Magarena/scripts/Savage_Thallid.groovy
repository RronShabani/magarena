[
    new MagicPermanentActivation(
        [MagicCondition.ONE_SAPROLING_CONDITION],
        new MagicActivationHints(MagicTiming.Pump),
        "Regen") {

        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return [
                new MagicSacrificePermanentEvent(
                    source,
                    source.getController(),
                    MagicTargetChoice.SACRIFICE_SAPROLING
                )
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(
                final MagicPermanent source,
                final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.POS_TARGET_FUNGUS_CREATURE,
                MagicRegenerateTargetPicker.getInstance(),
                this,
                "Regenerate target Fungus\$."
            );
        }

        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            event.processTargetPermanent(game,new MagicPermanentAction() {
                public void doAction(final MagicPermanent creature) {
                    game.doAction(new MagicRegenerateAction(creature));
                }
            });
        }
    }
]
