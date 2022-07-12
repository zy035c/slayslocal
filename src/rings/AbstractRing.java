package rings;

import actions.AbstractGameAction;
import actions.common.UseCardAction;
import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;
import dungeons.AbstractDungeon;
import localization.RingStrings;
import ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

public abstract class AbstractRing implements Comparable<AbstractRing> {

    // public static final String[] MSG = tutorialStrings.TEXT;
    // public static final String[] LABEL = tutorialStrings.LABEL;

    public final String name;
    public final String ringId;
    public final RingStrings ringStrings;
    // public final String[] DESCRIPTIONS;
    public boolean energyBased = false;
    public boolean usedUp = false;
    public boolean grayscale = false;
    public String description;
    public String flavorText = "missing";
    public int cost;
    public int counter = -1;
    public static final String IMG_DIR = "images/relics/";
    public static final String OUTLINE_DIR = "images/relics/outline/";
    private static final String L_IMG_DIR = "images/largeRelics/";
    public String imgUrl = "";
    public static final int RAW_W = 128;
    public static int ringPage = 0;
    private static float offsetX = 0.0F;
    public static final int MAX_RELICS_PER_PAGE = 25;
    public float currentX;
    public float currentY;
    public float targetX;
    public float targetY;
    public boolean isSeen = false;
    public RingTier tier;
    LandingSound landingSFX;
    public boolean isDone = false;
    public boolean isAnimating = false;
    public boolean isObtained = false;
    private static final float OBTAIN_SPEED = 6.0F;
    private static final float OBTAIN_THRESHOLD = 0.5F;
    private float rotation = 0.0F;
    public boolean discarded = false;
    private String assetURL;
    public enum LandingSound {
        CLINK, FLAT, HEAVY, MAGICAL, SOLID;
    }

    public enum RingTier {
        DEPRECATED, STARTER, COMMON, UNCOMMON, RARE, SPECIAL, BOSS, SHOP;
    }

    public AbstractRing(String name, String setId, String imgName, RingTier tier, LandingSound sfx) {
        this.name = name;
        this.ringId = setId;
        this.ringStrings = RingStrings.getRingStrings(this.ringId);
        // 没有意义的空架子 localization暂时为了不报错而建
        // for change language
        // this.DESCRIPTIONS = this.ringStrings.DESCRIPTIONS;
        this.imgUrl = imgName;

        // this.name = this.ringStrings.NAME;
        this.description = getUpdatedDescription();
        this.flavorText = this.ringStrings.FLAVOR; // 没用
        this.tier = tier;
        this.landingSFX = sfx;
        this.assetURL = "images/relics/" + imgName;
        // initializeTips();
    }

    public void usedUp() {
        this.grayscale = true;
        this.usedUp = true;
        // this.description = MSG[2];
//        this.tips.clear();
//        this.tips.add(new PowerTip(this.name, this.description));
        // initializeTips();
    }

//    public void spawn(float x, float y) {
//        if (!(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.ShopRoom)) {
//            AbstractDungeon.effectsQueue.add(new SmokePuffEffect(x, y));
//        }
//        this.currentX = x;
//        this.currentY = y;
//        this.isAnimating = true;
//        this.isObtained = false;
//        if (this.tier == RelicTier.BOSS) {
//            this.f_effect.x = 0.0F;
//            this.f_effect.y = 0.0F;
//            this.targetX = x;
//            this.targetY = y;
//            this.glowTimer = 0.0F;
//        } else {
//            this.f_effect.x = 0.0F;
//            this.f_effect.y = 0.0F;
//        }
//    }



    // I don't know it !!!???
//    public int getPrice() {
//        switch (this.tier) {
//            case LandingSound.CLINK:
//                return 300;
//            case LandingSound.FLAT:
//                return 150;
//            case LandingSound.SOLID:
//                return 250;
//            case LandingSound.HEAVY:
//                return 300;
//            case LandingSound.MAGICAL:
//                return 150;
////            case null:
////                return 400;
////            case null:
////                return 999;
////            case null:
////                return -1;
//            // not suppoted in current java edition ??!
//        }
//        return -1;
//    }

    // For animation
//    public void reorganizeObtain(AbstractPlayer p, int slot, boolean callOnEquip, int relicAmount) {
//        this.isDone = true;
//        this.isObtained = true;
//        p.relics.add(this);
//        this.currentX = START_X + slot * PAD_X;
//        this.currentY = START_Y;
//        this.targetX = this.currentX;
//        this.targetY = this.currentY;
//        this.hb.move(this.currentX, this.currentY);
//        if (callOnEquip) {
//            onEquip();
//            relicTip();
//        }
//        UnlockTracker.markRelicAsSeen(this.relicId);
//    }

//    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
//        if (this.relicId.equals("Circlet") && p != null && p.hasRelic("Circlet")) {
//            AbstractRing circ = p.getRelic("Circlet");
//            circ.counter++;
//            circ.flash();
//            this.isDone = true;
//            this.isObtained = true;
//            this.discarded = true;
//        } else {
//            this.isDone = true;
//            this.isObtained = true;
//            if (slot >= p.relics.size()) {
//                p.relics.add(this);
//            } else {
//                p.relics.set(slot, this);
//            }
//            this.currentX = START_X + slot * PAD_X;
//            this.currentY = START_Y;
//            this.targetX = this.currentX;
//            this.targetY = this.currentY;
//            this.hb.move(this.currentX, this.currentY);
//            if (callOnEquip) {
//                onEquip();
//                relicTip();
//            }
//            UnlockTracker.markRelicAsSeen(this.relicId);
//            getUpdatedDescription();
//            if (AbstractDungeon.topPanel != null) {
//                AbstractDungeon.topPanel.adjustRelicHbs();
//            }
//        }
//    }

//    public void instantObtain() {
//        if (this.relicId == "Circlet" && AbstractDungeon.onStagePlayer.hasRelic("Circlet")) {
//            AbstractRing circ = AbstractDungeon.onStagePlayer.getRelic("Circlet");
//            circ.counter++;
//            circ.flash();
//        } else {
//            playLandingSFX();
//            this.isDone = true;
//            this.isObtained = true;
//            this.currentX = START_X + AbstractDungeon.onStagePlayer.relics.size() * PAD_X;
//            this.currentY = START_Y;
//            this.targetX = this.currentX;
//            this.targetY = this.currentY;
//            flash();
//            AbstractDungeon.onStagePlayer.relics.add(this);
//            this.hb.move(this.currentX, this.currentY);
//            onEquip();
//            relicTip();
//            UnlockTracker.markRelicAsSeen(this.relicId);
//        }
//        if (AbstractDungeon.topPanel != null) {
//            AbstractDungeon.topPanel.adjustRelicHbs();
//        }
//    }

    public void obtain() {
        if (this.ringId == "Circlet" && AbstractDungeon.onStagePlayer.hasRing("Circlet")) {
            AbstractRing circ = AbstractDungeon.onStagePlayer.getRing("Circlet");
            circ.counter++;
            // circ.flash();
            // pure animation
            // this.hb.hovered = false; // hb is hitbox
        } else {
            // this.hb.hovered = false;
            int row = AbstractDungeon.onStagePlayer.rings.size();
            // this.targetX = START_X + row * PAD_X;
            // this.targetY = START_Y;
            AbstractDungeon.onStagePlayer.rings.add(this);
            // relicTip();
            // UnlockTracker.markRelicAsSeen(this.relicId);
        }
    }

    public int getColumn() {
        return AbstractDungeon.onStagePlayer.rings.indexOf(this);
    }

//    public void relicTip() {
//        if (TipTracker.relicCounter < 20) {
//            TipTracker.relicCounter++;
//            if (TipTracker.relicCounter >= 1 && !((Boolean) TipTracker.tips.get("RELIC_TIP")).booleanValue()) {
//                AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], 360.0F * Settings.scale, 760.0F * Settings.scale, FtueTip.TipType.RELIC);
//                TipTracker.neverShowAgain("RELIC_TIP");
//            }
//        }
//    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

//    public void update() {
//        updateFlash();
//        if (!this.isDone) {
//            if (this.isAnimating) {
//                this.glowTimer -= Gdx.graphics.getDeltaTime();
//                if (this.glowTimer < 0.0F) {
//                    this.glowTimer = 0.5F;
//                    AbstractDungeon.effectList.add(new GlowRelicParticle(this.img, this.currentX + this.f_effect.x, this.currentY + this.f_effect.y, this.rotation));
//                }
//                this.f_effect.update();
//                if (this.hb.hovered) {
//                    this.scale = Settings.scale * 1.5F;
//                } else {
//                    this.scale = MathHelper.scaleLerpSnap(this.scale, Settings.scale * 1.1F);
//                }
//            } else if (this.hb.hovered) {
//                this.scale = Settings.scale * 1.25F;
//            } else {
//                this.scale = MathHelper.scaleLerpSnap(this.scale, Settings.scale);
//            }
//            if (this.isObtained) {
//                if (this.rotation != 0.0F) {
//                    this.rotation = MathUtils.lerp(this.rotation, 0.0F, Gdx.graphics.getDeltaTime() * 6.0F * 2.0F);
//                }
//                if (this.currentX != this.targetX) {
//                    this.currentX = MathUtils.lerp(this.currentX, this.targetX, Gdx.graphics.getDeltaTime() * 6.0F);
//                    if (Math.abs(this.currentX - this.targetX) < 0.5F) {
//                        this.currentX = this.targetX;
//                    }
//                }
//                if (this.currentY != this.targetY) {
//                    this.currentY = MathUtils.lerp(this.currentY, this.targetY, Gdx.graphics.getDeltaTime() * 6.0F);
//                    if (Math.abs(this.currentY - this.targetY) < 0.5F) {
//                        this.currentY = this.targetY;
//                    }
//                }
//                if (this.currentY == this.targetY && this.currentX == this.targetX) {
//                    this.isDone = true;
//                    if (AbstractDungeon.topPanel != null) {
//                        AbstractDungeon.topPanel.adjustRelicHbs();
//                    }
//                    this.hb.move(this.currentX, this.currentY);
//                    if (this.tier == RelicTier.BOSS && AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.TreasureRoomBoss) {
//                        AbstractDungeon.overlayMenu.proceedButton.show();
//                    }
//                    onEquip();
//                }
//                this.scale = Settings.scale;
//            }
//            if (this.hb != null) {
//                this.hb.update();
//                if (this.hb.hovered && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NEOW_UNLOCK) {
//                    if (InputHelper.justClickedLeft && !this.isObtained) {
//                        InputHelper.justClickedLeft = false;
//                        this.hb.clickStarted = true;
//                    }
//                    if (this.hb.clicked || CInputActionSet.select.isJustPressed()) {
//                        CInputActionSet.select.unpress();
//                        this.hb.clicked = false;
//                        if (!Settings.isTouchScreen) {
//                            bossObtainLogic();
//                        } else {
//                            AbstractDungeon.bossRelicScreen.confirmButton.show();
//                            AbstractDungeon.bossRelicScreen.confirmButton.isDisabled = false;
//                            AbstractDungeon.bossRelicScreen.touchRelic = this;
//                        }
//                    }
//                }
//            }
//            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
//                updateAnimation();
//            }
//        } else {
//            if (AbstractDungeon.onStagePlayer != null && AbstractDungeon.onStagePlayer.relics.indexOf(this) / 25 == relicPage) {
//                this.hb.update();
//            } else {
//                this.hb.hovered = false;
//            }
//            if (this.hb.hovered && AbstractDungeon.topPanel.potionUi.isHidden) {
//                this.scale = Settings.scale * 1.25F;
//                CardCrawlGame.cursor.changeType(GameCursor.CursorType.INSPECT);
//            } else {
//                this.scale = MathHelper.scaleLerpSnap(this.scale, Settings.scale);
//            }
//            updateRelicPopupClick();
//        }
//    }

//    public void bossObtainLogic() {
//        if (!this.relicId.equals("HolyWater") && !this.relicId.equals("Black Blood") && !this.relicId.equals("Ring of the Serpent") &&
//                !this.relicId.equals("FrozenCore")) {
//            obtain();
//        }
//        this.isObtained = true;
//        this.f_effect.x = 0.0F;
//        this.f_effect.y = 0.0F;
//    }
//
//    private void updateRelicPopupClick() {
//        if (this.hb.hovered && InputHelper.justClickedLeft) {
//            this.hb.clickStarted = true;
//        }
//        if (this.hb.clicked || (this.hb.hovered && CInputActionSet.select.isJustPressed())) {
//            CardCrawlGame.relicPopup.open(this, AbstractDungeon.onStagePlayer.relics);
//            CInputActionSet.select.unpress();
//            this.hb.clicked = false;
//            this.hb.clickStarted = false;
//        }
//    }

    // public void updateDescription(AbstractPlayer.PlayerClass c) {}

    public String getUpdatedDescription() {
        return "";
    }

//    public void playLandingSFX() {
//        switch (this.landingSFX) {
//            case CLINK:
//                CardCrawlGame.sound.play("RELIC_DROP_CLINK");
//                return;
//            case FLAT:
//                CardCrawlGame.sound.play("RELIC_DROP_FLAT");
//                return;
//            case SOLID:
//                CardCrawlGame.sound.play("RELIC_DROP_ROCKY");
//                return;
//            case HEAVY:
//                CardCrawlGame.sound.play("RELIC_DROP_HEAVY");
//                return;
//            case MAGICAL:
//                CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
//                return;
//        }
//        CardCrawlGame.sound.play("RELIC_DROP_CLINK");
//    }

//    protected void updateAnimation() {
//        if (this.animationTimer != 0.0F) {
//            this.animationTimer -= Gdx.graphics.getDeltaTime();
//            if (this.animationTimer < 0.0F) {
//                this.animationTimer = 0.0F;
//            }
//        }
//    }

//    private void updateFlash() {
//        if (this.flashTimer != 0.0F) {
//            this.flashTimer -= Gdx.graphics.getDeltaTime();
//            if (this.flashTimer < 0.0F) {
//                if (this.pulse) {
//                    this.flashTimer = 1.0F;
//                } else {
//                    this.flashTimer = 0.0F;
//                }
//            }
//        }
//    }

    // public void onEvokeOrb(AbstractOrb ammo) {}

    public void onPlayCard(AbstractCard c, AbstractCreature m) {}

    public void onPreviewObtainCard(AbstractCard c) {
    }

    public void onObtainCard(AbstractCard c) {
    }

    public void onGainGold() {
    }

    public void onLoseGold() {
    }

    public void onSpendGold() {
    }

    public void onEquip() {
    }

    public void onUnequip() {
    }

    public void atPreBattle() {
    }

    public void atBattleStart() {
    }

    // public void onSpawnMonster(AbstractMonster monster) {}

    public void atBattleStartPreDraw() {
    }

    public void atTurnStart() {
    }

    public void atTurnStartPostDraw() {
    }

    public void onPlayerEndTurn() {
    }

    public void onBloodied() {
    }

    public void onNotBloodied() {
    }

    public void onManualDiscard() {
    }

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
    }

    public void onVictory() {
    }

    // public void onMonsterDeath(AbstractMonster m) {}

    public void onBlockBroken(AbstractCreature m) {
    }

    public int onPlayerGainBlock(int blockAmount) {
        return blockAmount;
    }

//    public int onPlayerGainedBlock(float blockAmount) {
//        return MathUtils.floor(blockAmount);
//    }

    public int onPlayerHeal(int healAmount) {
        return healAmount;
    }

    public void onMeditate() {
    }

    public void onEnergyRecharge() {
    }

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
    }

    public boolean canUseCampfireOption(AbstractCampfireOption option) {
        return true;
    }

    public void onRest() {}
    public void onRitual() {}
    public void onEnterRestRoom() {}
    public void onRefreshHand() {}
    public void onShuffle() {}
    public void onSmith() {}
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {}
    public int onAttacked(DamageInfo info, int damageAmount) {
        return damageAmount;
    }
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        return damageAmount;
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        return damageAmount;
    }
    public void onExhaust(AbstractCard card) {}
    public void onTrigger() {}
    public void onTrigger(AbstractCreature target) {}
    public boolean checkTrigger() {
        return false;
    }
    public void onCardDraw(AbstractCard drawnCard) {}
    public void onChestOpen(boolean bossChest) {}
    public void onChestOpenAfter(boolean bossChest) {}
    public void onDrawOrDiscard() {}
    public void onMasterDeckChange() {}
    public float atDamageModify(float damage, AbstractCard c) {
        return damage;
    }
    public int changeNumberOfCardsInReward(int numberOfCards) {
        return numberOfCards;
    }
    public int changeRareCardRewardChance(int rareCardChance) {
        return rareCardChance;
    }
    public int changeUncommonCardRewardChance(int uncommonCardChance) {
        return uncommonCardChance;
    }


    public boolean canPlay(AbstractCard card) {
        return true;
    }

//    public static String gameDataUploadHeader() {
//        GameDataStringBuilder builder = new GameDataStringBuilder();
//        builder.addFieldData("name");
//        builder.addFieldData("relicID");
//        builder.addFieldData("color");
//        builder.addFieldData("description");
//        builder.addFieldData("flavorText");
//        builder.addFieldData("cost");
//        builder.addFieldData("tier");
//        builder.addFieldData("assetURL");
//        return builder.toString();
//    }

//    protected void initializeTips() {
//        Scanner desc = new Scanner(this.description);
//        while (desc.hasNext()) {
//            String s = desc.next();
//            if (s.charAt(0) == '#') {
//                s = s.substring(2);
//            }
//            s = s.replace(',', ' ');
//            s = s.replace('.', ' ');
//            s = s.trim();
//            s = s.toLowerCase();
//            boolean alreadyExists = false;
//            if (GameDictionary.keywords.containsKey(s)) {
//                s = (String) GameDictionary.parentWord.get(s);
//                for (PowerTip t : this.tips) {
//                    if (t.header.toLowerCase().equals(s)) {
//                        alreadyExists = true;
//                        break;
//                    }
//                }
//                if (!alreadyExists) {
//                    this.tips.add(new PowerTip(TipHelper.capitalize(s), (String) GameDictionary.keywords.get(s)));
//                }
//            }
//        }
//        desc.close();
//    }

//    public String gameDataUploadData(String color) {
//        GameDataStringBuilder builder = new GameDataStringBuilder();
//        builder.addFieldData(this.name);
//        builder.addFieldData(this.relicId);
//        builder.addFieldData(color);
//        builder.addFieldData(this.description);
//        builder.addFieldData(this.flavorText);
//        builder.addFieldData(this.cost);
//        builder.addFieldData(this.tier.name());
//        builder.addFieldData(this.assetURL);
//        return builder.toString();
//    }

    public String toString() {
        return this.name;
    }

    public int compareTo(AbstractRing arg0) {
        return this.name.compareTo(arg0.name);
    }

    public String getAssetURL() {
        return this.assetURL;
    }

//    public HashMap<String, Serializable> getLocStrings() {
//        HashMap<String, Serializable> relicData = new HashMap<>();
//        relicData.put("name", this.name);
//        relicData.put("description", this.description);
//        return relicData;
//    }

    public boolean canSpawn() {
        return true;
    }

    public void onUsePotion() {}


    public void onLoseHp(int damageAmount) {
    }


//    public void loadLargeImg() {
//        if (this.largeImg == null) {
//            this.largeImg = ImageMaster.loadImage("images/largeRelics/" + this.imgUrl);
//        }
//    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public int onLoseHpLast(int damageAmount) {
        return damageAmount;
    }

    public void wasHPLost(int damageAmount) {
    }

    public abstract AbstractRing makeCopy();

}


