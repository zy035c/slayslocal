package rings;

import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;

public abstract class AbstractRing implements Comparable<AbstractRing> {
    // public static final String[] MSG = tutorialStrings.TEXT;
    // public static final String[] LABEL = tutorialStrings.LABEL;

    public final String name;
    public final String ringId;
    // ? private final RelicStrings relicStrings;
    public final String[] DESCRIPTIONS;
    // ? public static final String USED_UP_MSG = MSG[2];
    public boolean energyBased = false;
    public boolean usedUp = false;
    public boolean grayscale = false;
    public String description;
    public String flavorText = "missing";
    public int cost;
    public int counter = -1;
    // ? public RelicTier tier;
    // ?  public ArrayList<PowerTip> tips = new ArrayList<>();
    // public Texture img;
    // public Texture largeImg;
    // public Texture outlineImg;
    /*      */   public static final String IMG_DIR = "images/relics/";
    /*      */   public static final String OUTLINE_DIR = "images/relics/outline/";
    /*      */   private static final String L_IMG_DIR = "images/largeRelics/";
    public String imgUrl = "";
    /*      */
    public static final int RAW_W = 128;
    /*   79 */   public static int ringPage = 0;
    private static float offsetX = 0.0F;
    public static final int MAX_RELICS_PER_PAGE = 25;
    /*      */   public float currentX;
    /*      */   public float currentY;
    /*      */   public float targetX;
    /*      */   public float targetY;
//    /*   85 */   private static final float START_X = 64.0F * Settings.scale;
//    private static final float START_Y = Settings.HEIGHT - 102.0F * Settings.scale;
//    /*   86 */   public static final float PAD_X = 72.0F * Settings.scale;
//    /*   87 */   public static final Color PASSIVE_OUTLINE_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.33F);
//    /*   88 */   private Color flashColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
//    /*   89 */   private Color goldOutlineColor = new Color(1.0F, 0.9F, 0.4F, 0.0F);
    /*      */
    public boolean isSeen = false;
    // public float scale = Settings.scale;

//    /*      */   protec ted boolean pulse = false;
//    /*      */   private float animationTimer;
//    /*   96 */   private float glowTimer = 0.0F;
//    /*   97 */   public float flashTimer = 0.0F;
//    /*      */   private static final float FLASH_ANIM_TIME = 2.0F;
//    /*      */   private static final float DEFAULT_ANIM_SCALE = 4.0F;
    // private FloatyEffect f_effect = new FloatyEffect(10.0F, 0.2F);
    public boolean isDone = false;
    public boolean isAnimating = false;
    public boolean isObtained = false;
    // private LandingSound landingSFX = LandingSound.CLINK;
    // public Hitbox hb = new Hitbox(PAD_X, PAD_X);
    private static final float OBTAIN_SPEED = 6.0F;
    private static final float OBTAIN_THRESHOLD = 0.5F;
    private float rotation = 0.0F;
    public boolean discarded = false;
    private String assetURL;
    public enum LandingSound {CLINK, FLAT, HEAVY, MAGICAL, SOLID;}

    public enum RingTier {
        DEPRECATED, STARTER, COMMON, UNCOMMON, RARE, SPECIAL, BOSS, SHOP;
    }

    /*      */
    /*      */
    /*      */
    /*      */
    public AbstractRing(String setId, String imgName, RingTier tier, LandingSound sfx) {
        /*  122 */
        this.relicId = setId;
        /*  123 */
        this.relicStrings = CardCrawlGame.languagePack.getRelicStrings(this.relicId);
        /*  124 */
        this.DESCRIPTIONS = this.relicStrings.DESCRIPTIONS;
        /*  125 */
        this.imgUrl = imgName;
        /*  126 */
        ImageMaster.loadRelicImg(setId, imgName);
        /*  127 */
        this.img = ImageMaster.getRelicImg(setId);
        /*  128 */
        this.outlineImg = ImageMaster.getRelicOutlineImg(setId);
        /*  129 */
        this.name = this.relicStrings.NAME;
        /*  130 */
        this.description = getUpdatedDescription();
        /*  131 */
        this.flavorText = this.relicStrings.FLAVOR;
        /*  132 */
        this.tier = tier;
        /*  133 */
        this.landingSFX = sfx;
        /*  134 */
        this.assetURL = "images/relics/" + imgName;
        /*  135 */
        this.tips.add(new PowerTip(this.name, this.description));
        /*  136 */
        initializeTips();
        /*      */
    }

    /*      */
    /*      */
    public void usedUp() {
        /*  140 */
        this.grayscale = true;
        /*  141 */
        this.usedUp = true;
        /*  142 */
        this.description = MSG[2];
        /*  143 */
        this.tips.clear();
        /*  144 */
        this.tips.add(new PowerTip(this.name, this.description));
        /*  145 */
        initializeTips();
        /*      */
    }

    /*      */
    /*      */
    public void spawn(float x, float y) {
        /*  149 */
        if (!(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.ShopRoom)) {
            /*  150 */
            AbstractDungeon.effectsQueue.add(new SmokePuffEffect(x, y));
            /*      */
        }
        /*  152 */
        this.currentX = x;
        /*  153 */
        this.currentY = y;
        /*  154 */
        this.isAnimating = true;
        /*  155 */
        this.isObtained = false;
        /*      */
        /*  157 */
        if (this.tier == RelicTier.BOSS) {
            /*  158 */
            this.f_effect.x = 0.0F;
            /*  159 */
            this.f_effect.y = 0.0F;
            /*  160 */
            this.targetX = x;
            /*  161 */
            this.targetY = y;
            /*  162 */
            this.glowTimer = 0.0F;
            /*      */
        } else {
            /*  164 */
            this.f_effect.x = 0.0F;
            /*  165 */
            this.f_effect.y = 0.0F;
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    public int getPrice() {
        /*  170 */
        switch (this.tier) {
            /*      */
            case CLINK:
                /*  172 */
                return 300;
            /*      */
            case FLAT:
                /*  174 */
                return 150;
            /*      */
            case SOLID:
                /*  176 */
                return 250;
            /*      */
            case HEAVY:
                /*  178 */
                return 300;
            /*      */
            case MAGICAL:
                /*  180 */
                return 150;
            /*      */
            case null:
                /*  182 */
                return 400;
            /*      */
            case null:
                /*  184 */
                return 999;
            /*      */
            case null:
                /*  186 */
                return -1;
            /*      */
        }
        /*  188 */
        return -1;
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void reorganizeObtain(AbstractPlayer p, int slot, boolean callOnEquip, int relicAmount) {
        /*  200 */
        this.isDone = true;
        /*  201 */
        this.isObtained = true;
        /*  202 */
        p.relics.add(this);
        /*  203 */
        this.currentX = START_X + slot * PAD_X;
        /*  204 */
        this.currentY = START_Y;
        /*  205 */
        this.targetX = this.currentX;
        /*  206 */
        this.targetY = this.currentY;
        /*  207 */
        this.hb.move(this.currentX, this.currentY);
        /*  208 */
        if (callOnEquip) {
            /*  209 */
            onEquip();
            /*  210 */
            relicTip();
            /*      */
        }
        /*  212 */
        UnlockTracker.markRelicAsSeen(this.relicId);
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
        /*  223 */
        if (this.relicId.equals("Circlet") && p != null && p.hasRelic("Circlet")) {
            /*  224 */
            AbstractRelic circ = p.getRelic("Circlet");
            /*  225 */
            circ.counter++;
            /*  226 */
            circ.flash();
            /*      */
            /*  228 */
            this.isDone = true;
            /*  229 */
            this.isObtained = true;
            /*  230 */
            this.discarded = true;
            /*      */
        } else {
            /*  232 */
            this.isDone = true;
            /*  233 */
            this.isObtained = true;
            /*      */
            /*  235 */
            if (slot >= p.relics.size()) {
                /*  236 */
                p.relics.add(this);
                /*      */
            } else {
                /*  238 */
                p.relics.set(slot, this);
                /*      */
            }
            /*      */
            /*  241 */
            this.currentX = START_X + slot * PAD_X;
            /*  242 */
            this.currentY = START_Y;
            /*  243 */
            this.targetX = this.currentX;
            /*  244 */
            this.targetY = this.currentY;
            /*  245 */
            this.hb.move(this.currentX, this.currentY);
            /*      */
            /*  247 */
            if (callOnEquip) {
                /*  248 */
                onEquip();
                /*  249 */
                relicTip();
                /*      */
            }
            /*      */
            /*  252 */
            UnlockTracker.markRelicAsSeen(this.relicId);
            /*  253 */
            getUpdatedDescription();
            /*      */
            /*  255 */
            if (AbstractDungeon.topPanel != null) {
                /*  256 */
                AbstractDungeon.topPanel.adjustRelicHbs();
                /*      */
            }
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    public void instantObtain() {
        /*  262 */
        if (this.relicId == "Circlet" && AbstractDungeon.player.hasRelic("Circlet")) {
            /*  263 */
            AbstractRelic circ = AbstractDungeon.player.getRelic("Circlet");
            /*  264 */
            circ.counter++;
            /*  265 */
            circ.flash();
            /*      */
        } else {
            /*  267 */
            playLandingSFX();
            /*  268 */
            this.isDone = true;
            /*  269 */
            this.isObtained = true;
            /*  270 */
            this.currentX = START_X + AbstractDungeon.player.relics.size() * PAD_X;
            /*  271 */
            this.currentY = START_Y;
            /*  272 */
            this.targetX = this.currentX;
            /*  273 */
            this.targetY = this.currentY;
            /*  274 */
            flash();
            /*      */
            /*  276 */
            AbstractDungeon.player.relics.add(this);
            /*  277 */
            this.hb.move(this.currentX, this.currentY);
            /*  278 */
            onEquip();
            /*  279 */
            relicTip();
            /*  280 */
            UnlockTracker.markRelicAsSeen(this.relicId);
            /*      */
        }
        /*  282 */
        if (AbstractDungeon.topPanel != null) {
            /*  283 */
            AbstractDungeon.topPanel.adjustRelicHbs();
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void obtain() {
        /*  291 */
        if (this.relicId == "Circlet" && AbstractDungeon.player.hasRelic("Circlet")) {
            /*  292 */
            AbstractRelic circ = AbstractDungeon.player.getRelic("Circlet");
            /*  293 */
            circ.counter++;
            /*  294 */
            circ.flash();
            /*  295 */
            this.hb.hovered = false;
            /*      */
        } else {
            /*  297 */
            this.hb.hovered = false;
            /*  298 */
            int row = AbstractDungeon.player.relics.size();
            /*  299 */
            this.targetX = START_X + row * PAD_X;
            /*  300 */
            this.targetY = START_Y;
            /*  301 */
            AbstractDungeon.player.relics.add(this);
            /*  302 */
            relicTip();
            /*  303 */
            UnlockTracker.markRelicAsSeen(this.relicId);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    public int getColumn() {
        /*  308 */
        return AbstractDungeon.player.relics.indexOf(this);
        /*      */
    }

    /*      */
    /*      */
    /*      */
    public void relicTip() {
        /*  313 */
        if (TipTracker.relicCounter < 20) {
            /*  314 */
            TipTracker.relicCounter++;
            /*  315 */
            if (TipTracker.relicCounter >= 1 && !((Boolean) TipTracker.tips.get("RELIC_TIP")).booleanValue()) {
                /*  316 */
                AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], 360.0F * Settings.scale, 760.0F * Settings.scale, FtueTip.TipType.RELIC);
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*  322 */
                TipTracker.neverShowAgain("RELIC_TIP");
                /*      */
            }
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    public void setCounter(int counter) {
        /*  329 */
        this.counter = counter;
        /*      */
    }

    /*      */
    /*      */
    public void update() {
        /*  333 */
        updateFlash();
        /*      */
        /*  335 */
        if (!this.isDone) {
            /*      */
            /*  337 */
            if (this.isAnimating) {
                /*  338 */
                this.glowTimer -= Gdx.graphics.getDeltaTime();
                /*  339 */
                if (this.glowTimer < 0.0F) {
                    /*  340 */
                    this.glowTimer = 0.5F;
                    /*  341 */
                    AbstractDungeon.effectList.add(new GlowRelicParticle(this.img, this.currentX + this.f_effect.x, this.currentY + this.f_effect.y, this.rotation));
                    /*      */
                }
                /*      */
                /*  344 */
                this.f_effect.update();
                /*  345 */
                if (this.hb.hovered) {
                    /*  346 */
                    this.scale = Settings.scale * 1.5F;
                    /*      */
                } else {
                    /*  348 */
                    this.scale = MathHelper.scaleLerpSnap(this.scale, Settings.scale * 1.1F);
                    /*      */
                }
                /*      */
                /*  351 */
            } else if (this.hb.hovered) {
                /*  352 */
                this.scale = Settings.scale * 1.25F;
                /*      */
            } else {
                /*  354 */
                this.scale = MathHelper.scaleLerpSnap(this.scale, Settings.scale);
                /*      */
            }
            /*      */
            /*      */
            /*      */
            /*  359 */
            if (this.isObtained) {
                /*  360 */
                if (this.rotation != 0.0F) {
                    /*  361 */
                    this.rotation = MathUtils.lerp(this.rotation, 0.0F, Gdx.graphics.getDeltaTime() * 6.0F * 2.0F);
                    /*      */
                }
                /*      */
                /*  364 */
                if (this.currentX != this.targetX) {
                    /*  365 */
                    this.currentX = MathUtils.lerp(this.currentX, this.targetX, Gdx.graphics.getDeltaTime() * 6.0F);
                    /*  366 */
                    if (Math.abs(this.currentX - this.targetX) < 0.5F) {
                        /*  367 */
                        this.currentX = this.targetX;
                        /*      */
                    }
                    /*      */
                }
                /*  370 */
                if (this.currentY != this.targetY) {
                    /*  371 */
                    this.currentY = MathUtils.lerp(this.currentY, this.targetY, Gdx.graphics.getDeltaTime() * 6.0F);
                    /*  372 */
                    if (Math.abs(this.currentY - this.targetY) < 0.5F) {
                        /*  373 */
                        this.currentY = this.targetY;
                        /*      */
                    }
                    /*      */
                }
                /*      */
                /*  377 */
                if (this.currentY == this.targetY && this.currentX == this.targetX) {
                    /*  378 */
                    this.isDone = true;
                    /*  379 */
                    if (AbstractDungeon.topPanel != null) {
                        /*  380 */
                        AbstractDungeon.topPanel.adjustRelicHbs();
                        /*      */
                    }
                    /*  382 */
                    this.hb.move(this.currentX, this.currentY);
                    /*  383 */
                    if (this.tier == RelicTier.BOSS && AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.TreasureRoomBoss) {
                        /*  384 */
                        AbstractDungeon.overlayMenu.proceedButton.show();
                        /*      */
                    }
                    /*  386 */
                    onEquip();
                    /*      */
                }
                /*  388 */
                this.scale = Settings.scale;
                /*      */
            }
            /*      */
            /*  391 */
            if (this.hb != null) {
                /*  392 */
                this.hb.update();
                /*      */
                /*  394 */
                if (this.hb.hovered && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NEOW_UNLOCK) {
                    /*      */
                    /*      */
                    /*  397 */
                    if (InputHelper.justClickedLeft && !this.isObtained) {
                        /*  398 */
                        InputHelper.justClickedLeft = false;
                        /*  399 */
                        this.hb.clickStarted = true;
                        /*      */
                    }
                    /*      */
                    /*  402 */
                    if (this.hb.clicked || CInputActionSet.select.isJustPressed()) {
                        /*  403 */
                        CInputActionSet.select.unpress();
                        /*  404 */
                        this.hb.clicked = false;
                        /*      */
                        /*  406 */
                        if (!Settings.isTouchScreen) {
                            /*  407 */
                            bossObtainLogic();
                            /*      */
                        } else {
                            /*  409 */
                            AbstractDungeon.bossRelicScreen.confirmButton.show();
                            /*  410 */
                            AbstractDungeon.bossRelicScreen.confirmButton.isDisabled = false;
                            /*  411 */
                            AbstractDungeon.bossRelicScreen.touchRelic = this;
                            /*      */
                        }
                        /*      */
                    }
                    /*      */
                }
                /*      */
            }
            /*  416 */
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
                /*  417 */
                updateAnimation();
                /*      */
            }
            /*      */
        } else {
            /*  420 */
            if (AbstractDungeon.player != null && AbstractDungeon.player.relics.indexOf(this) / 25 == relicPage) {
                /*      */
                /*  422 */
                this.hb.update();
                /*      */
            } else {
                /*  424 */
                this.hb.hovered = false;
                /*      */
            }
            /*      */
            /*  427 */
            if (this.hb.hovered && AbstractDungeon.topPanel.potionUi.isHidden) {
                /*  428 */
                this.scale = Settings.scale * 1.25F;
                /*  429 */
                CardCrawlGame.cursor.changeType(GameCursor.CursorType.INSPECT);
                /*      */
            } else {
                /*  431 */
                this.scale = MathHelper.scaleLerpSnap(this.scale, Settings.scale);
                /*      */
            }
            /*  433 */
            updateRelicPopupClick();
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    public void bossObtainLogic() {
        /*  438 */
        if (!this.relicId.equals("HolyWater") && !this.relicId.equals("Black Blood") && !this.relicId.equals("Ring of the Serpent") &&
                /*  439 */       !this.relicId.equals("FrozenCore")) {
            /*  440 */
            obtain();
            /*      */
        }
        /*  442 */
        this.isObtained = true;
        /*  443 */
        this.f_effect.x = 0.0F;
        /*  444 */
        this.f_effect.y = 0.0F;
        /*      */
    }

    /*      */
    /*      */
    private void updateRelicPopupClick() {
        /*  448 */
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            /*  449 */
            this.hb.clickStarted = true;
            /*      */
        }
        /*  451 */
        if (this.hb.clicked || (this.hb.hovered && CInputActionSet.select.isJustPressed())) {
            /*  452 */
            CardCrawlGame.relicPopup.open(this, AbstractDungeon.player.relics);
            /*  453 */
            CInputActionSet.select.unpress();
            /*  454 */
            this.hb.clicked = false;
            /*  455 */
            this.hb.clickStarted = false;
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    public void updateDescription(AbstractPlayer.PlayerClass c) {
    }

    /*      */
    /*      */
    public String getUpdatedDescription() {
        /*  463 */
        return "";
        /*      */
    }

    /*      */
    /*      */
    public void playLandingSFX() {
        /*  467 */
        switch (this.landingSFX) {
            /*      */
            case CLINK:
                /*  469 */
                CardCrawlGame.sound.play("RELIC_DROP_CLINK");
                /*      */
                return;
            /*      */
            case FLAT:
                /*  472 */
                CardCrawlGame.sound.play("RELIC_DROP_FLAT");
                /*      */
                return;
            /*      */
            case SOLID:
                /*  475 */
                CardCrawlGame.sound.play("RELIC_DROP_ROCKY");
                /*      */
                return;
            /*      */
            case HEAVY:
                /*  478 */
                CardCrawlGame.sound.play("RELIC_DROP_HEAVY");
                /*      */
                return;
            /*      */
            case MAGICAL:
                /*  481 */
                CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
                /*      */
                return;
            /*      */
        }
        /*  484 */
        CardCrawlGame.sound.play("RELIC_DROP_CLINK");
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    protected void updateAnimation() {
        /*  493 */
        if (this.animationTimer != 0.0F) {
            /*  494 */
            this.animationTimer -= Gdx.graphics.getDeltaTime();
            /*  495 */
            if (this.animationTimer < 0.0F) {
                /*  496 */
                this.animationTimer = 0.0F;
                /*      */
            }
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    private void updateFlash() {
        /*  502 */
        if (this.flashTimer != 0.0F) {
            /*  503 */
            this.flashTimer -= Gdx.graphics.getDeltaTime();
            /*  504 */
            if (this.flashTimer < 0.0F) {
                /*  505 */
                if (this.pulse) {
                    /*  506 */
                    this.flashTimer = 1.0F;
                    /*      */
                } else {
                    /*  508 */
                    this.flashTimer = 0.0F;
                    /*      */
                }
                /*      */
            }
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    public void onEvokeOrb(AbstractOrb ammo) {
    }

    /*      */
    /*      */
    /*      */
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
    }

    /*      */
    /*      */
    /*      */
    public void onPreviewObtainCard(AbstractCard c) {
    }

    /*      */
    /*      */
    /*      */
    public void onObtainCard(AbstractCard c) {
    }

    /*      */
    /*      */
    /*      */
    public void onGainGold() {
    }

    /*      */
    /*      */
    /*      */
    public void onLoseGold() {
    }

    /*      */
    /*      */
    /*      */
    public void onSpendGold() {
    }

    /*      */
    /*      */
    /*      */
    public void onEquip() {
    }

    /*      */
    /*      */
    /*      */
    public void onUnequip() {
    }

    /*      */
    /*      */
    /*      */
    public void atPreBattle() {
    }

    /*      */
    /*      */
    /*      */
    public void atBattleStart() {
    }

    /*      */
    /*      */
    /*      */
    public void onSpawnMonster(AbstractMonster monster) {
    }

    /*      */
    /*      */
    /*      */
    public void atBattleStartPreDraw() {
    }

    /*      */
    /*      */
    /*      */
    public void atTurnStart() {
    }

    /*      */
    /*      */
    /*      */
    public void atTurnStartPostDraw() {
    }

    /*      */
    /*      */
    /*      */
    public void onPlayerEndTurn() {
    }

    /*      */
    /*      */
    /*      */
    public void onBloodied() {
    }

    /*      */
    /*      */
    /*      */
    public void onNotBloodied() {
    }

    /*      */
    /*      */
    /*      */
    public void onManualDiscard() {
    }

    /*      */
    /*      */
    /*      */
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
    }

    /*      */
    /*      */
    /*      */
    public void onVictory() {
    }

    /*      */
    /*      */
    /*      */
    public void onMonsterDeath(AbstractMonster m) {
    }

    /*      */
    /*      */
    /*      */
    public void onBlockBroken(AbstractCreature m) {
    }

    /*      */
    /*      */
    /*      */
    public int onPlayerGainBlock(int blockAmount) {
        /*  586 */
        return blockAmount;
        /*      */
    }

    /*      */
    /*      */
    public int onPlayerGainedBlock(float blockAmount) {
        /*  590 */
        return MathUtils.floor(blockAmount);
        /*      */
    }

    /*      */
    /*      */
    public int onPlayerHeal(int healAmount) {
        /*  594 */
        return healAmount;
        /*      */
    }

    /*      */
    /*      */
    /*      */
    public void onMeditate() {
    }

    /*      */
    /*      */
    /*      */
    public void onEnergyRecharge() {
    }

    /*      */
    /*      */
    /*      */
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
    }

    /*      */
    /*      */
    public boolean canUseCampfireOption(AbstractCampfireOption option) {
        /*  607 */
        return true;
        /*      */
    }

    /*      */
    /*      */
    /*      */
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

    /*      */
    /*      */
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        return damageAmount;
    }
    public void onExhaust(AbstractCard card) {}
    public void onTrigger() {}
    public void onTrigger(AbstractCreature target) {}
    public boolean checkTrigger() {
        return false;
    }
    // public void onEnterRoom(AbstractRoom room) {}
    // public void justEnteredRoom(AbstractRoom room) {
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
    public void renderInTopPanel(SpriteBatch sb) {
        /*  697 */
        if (Settings.hideRelics) {
            /*      */
            return;
            /*      */
        }
        /*      */
        /*  701 */
        renderOutline(sb, true);
        /*  702 */
        if (this.grayscale) {
            /*  703 */
            ShaderHelper.setShader(sb, ShaderHelper.Shader.GRAYSCALE);
        }
        sb.setColor(Color.WHITE);
        sb.draw(this.img, this.currentX - 64.0F + offsetX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
        if (this.grayscale) {
            /*  724 */
            ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
            /*      */
        }
        /*  726 */
        renderCounter(sb, true);
        /*  727 */
        renderFlash(sb, true);
        /*  728 */
        this.hb.render(sb);
        /*      */
    }

    public void render(SpriteBatch sb) {
        /*  732 */
        if (Settings.hideRelics) {
            /*      */
            return;
            /*      */
        }
        renderOutline(sb, false);
        /*  738 */
        if (!this.isObtained && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP)) {
            if (this.hb.hovered) {
                /*  742 */
                renderBossTip(sb);
                /*      */
            }
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
                /*  746 */
                if (this.hb.hovered) {
                    /*  747 */
                    sb.setColor(PASSIVE_OUTLINE_COLOR);
                    /*  748 */
                    sb.draw(this.outlineImg, this.currentX - 64.0F + this.f_effect.x, this.currentY - 64.0F + this.f_effect.y, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
                    /*      */
                }
                /*      */
                else {
                    sb.setColor(PASSIVE_OUTLINE_COLOR);
                    /*  767 */
                    sb.draw(this.outlineImg, this.currentX - 64.0F + this.f_effect.x, this.currentY - 64.0F + this.f_effect.y, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
                    /*      */
                }
                /*      */
            }
            /*      */
        }
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
            /*  789 */
            if (!this.isObtained) {
                /*  790 */
                sb.setColor(Color.WHITE);
                /*  791 */
                sb.draw(this.img, this.currentX - 64.0F + this.f_effect.x, this.currentY - 64.0F + this.f_effect.y, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
            }
            /*      */
            else {
                sb.setColor(Color.WHITE);
                /*  810 */
                sb.draw(this.img, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
                renderCounter(sb, false);
                /*      */
            }
            /*      */
        } else {
            /*  830 */
            sb.setColor(Color.WHITE);
            /*  831 */
            sb.draw(this.img, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
            renderCounter(sb, false);
            /*      */
        }
        /*      */
        /*  852 */
        if (this.isDone) {
            /*  853 */
            renderFlash(sb, false);
            /*      */
        }
        /*      */
        /*  856 */
        this.hb.render(sb);
        /*      */
    }

    /*      */
    /*      */
    public void renderLock(SpriteBatch sb, Color outlineColor) {
        /*  860 */
        sb.setColor(outlineColor);
        /*  861 */
        sb.draw(ImageMaster.RELIC_LOCK_OUTLINE, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
        sb.setColor(Color.WHITE);
        /*  880 */
        sb.draw(ImageMaster.RELIC_LOCK, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*  898 */
        if (this.hb.hovered) {
            /*  899 */
            String unlockReq = (String) UnlockTracker.unlockReqs.get(this.relicId);
            /*  900 */
            if (unlockReq == null) {
                /*  901 */
                unlockReq = "Missing unlock req.";
                /*      */
            }
            /*  903 */
            unlockReq = LABEL[2];
            /*      */
            /*  905 */
            if (InputHelper.mX < 1400.0F * Settings.scale) {
                /*  906 */
                if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW && InputHelper.mY < Settings.HEIGHT / 5.0F)
                    /*      */ {
                    /*  908 */
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY + 100.0F * Settings.scale, LABEL[3], unlockReq);
                    /*      */
                    /*      */
                }
                /*      */
                else
                    /*      */ {
                    /*      */
                    /*  914 */
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY - 50.0F * Settings.scale, LABEL[3], unlockReq);
                    /*      */
                    /*      */
                }
                /*      */
                /*      */
            }
            /*      */
            else {
                /*      */
                /*  921 */
                TipHelper.renderGenericTip(InputHelper.mX - 350.0F * Settings.scale, InputHelper.mY - 50.0F * Settings.scale, LABEL[3], unlockReq);
                /*      */
            }
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*  928 */
            float tmpX = this.currentX;
            /*  929 */
            float tmpY = this.currentY;
            /*      */
            /*  931 */
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
                /*  932 */
                tmpX += this.f_effect.x;
                /*  933 */
                tmpY += this.f_effect.y;
                /*      */
            }
            /*      */
            /*  936 */
            sb.setColor(Color.WHITE);
            /*  937 */
            sb.draw(ImageMaster.RELIC_LOCK, tmpX - 64.0F, tmpY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
            /*      */
        }
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*  956 */
        this.hb.render(sb);
        /*      */
    }

    /*      */
    /*      */
    public void render(SpriteBatch sb, boolean renderAmount, Color outlineColor) {
        /*  960 */
        if (this.isSeen) {
            /*  961 */
            renderOutline(outlineColor, sb, false);
            /*      */
        } else {
            /*  963 */
            renderOutline(Color.LIGHT_GRAY, sb, false);
            /*      */
        }
        /*      */
        /*  966 */
        if (this.isSeen) {
            /*  967 */
            sb.setColor(Color.WHITE);
            /*      */
        }
        /*  969 */
        else if (this.hb.hovered) {
            /*  970 */
            sb.setColor(Settings.HALF_TRANSPARENT_BLACK_COLOR);
            /*      */
        } else {
            /*  972 */
            sb.setColor(Color.BLACK);
            /*      */
        }
        /*      */
        /*      */
        /*  976 */
        if (AbstractDungeon.screen != null && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.NEOW_UNLOCK) {
            /*  977 */
            if (this.largeImg == null)
                /*      */ {
                /*  979 */
                sb.draw(this.img, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * 2.0F +
                        /*      */
                        /*      */
                        /*      */
                        /*      */
                        /*      */
                        /*      */
                        /*      */
                        /*  987 */             MathUtils.cosDeg((float) (System.currentTimeMillis() / 5L % 360L)) / 15.0F, Settings.scale * 2.0F +
                        /*  988 */             MathUtils.cosDeg((float) (System.currentTimeMillis() / 5L % 360L)) / 15.0F, this.rotation, 0, 0, 128, 128, false, false);
                /*      */
                /*      */
                /*      */
                /*      */
            }
            /*      */
            else
                /*      */ {
                /*      */
                /*      */
                /*  997 */
                sb.draw(this.largeImg, this.currentX - 128.0F, this.currentY - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, Settings.scale * 1.0F +
                        /*      */
                        /*      */
                        /*      */
                        /*      */
                        /*      */
                        /*      */
                        /*      */
                        /* 1005 */             MathUtils.cosDeg((float) (System.currentTimeMillis() / 5L % 360L)) / 30.0F, Settings.scale * 1.0F +
                        /* 1006 */             MathUtils.cosDeg((float) (System.currentTimeMillis() / 5L % 360L)) / 30.0F, this.rotation, 0, 0, 256, 256, false, false);
                /*      */
                /*      */
                /*      */
            }
            /*      */
            /*      */
            /*      */
        }
        /*      */
        else {
            /*      */
            /*      */
            /* 1016 */
            sb.draw(this.img, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /* 1033 */
            if (this.relicId.equals("Circlet")) {
                /* 1034 */
                renderCounter(sb, false);
                /*      */
            }
            /*      */
        }
        /*      */
        /* 1038 */
        if (this.hb.hovered && !CardCrawlGame.relicPopup.isOpen) {
            /* 1039 */
            if (!this.isSeen) {
                /* 1040 */
                if (InputHelper.mX < 1400.0F * Settings.scale) {
                    /* 1041 */
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY - 50.0F * Settings.scale, LABEL[1], MSG[1]);
                    /*      */
                    /*      */
                }
                /*      */
                else {
                    /*      */
                    /*      */
                    /* 1047 */
                    TipHelper.renderGenericTip(InputHelper.mX - 350.0F * Settings.scale, InputHelper.mY - 50.0F * Settings.scale, LABEL[1], MSG[1]);
                    /*      */
                }
                /*      */
                /*      */
                /*      */
                return;
                /*      */
            }
            /*      */
            /*      */
            /* 1055 */
            renderTip(sb);
            /*      */
        }
        /*      */
        /*      */
        /* 1059 */
        this.hb.render(sb);
        /*      */
    }

    /*      */
    /*      */
    public void renderWithoutAmount(SpriteBatch sb, Color c) {
        /* 1063 */
        renderOutline(c, sb, false);
        /* 1064 */
        sb.setColor(Color.WHITE);
        /* 1065 */
        sb.draw(this.img, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /* 1083 */
        if (this.hb.hovered) {
            /* 1084 */
            renderTip(sb);
            /*      */
            /* 1086 */
            float tmpX = this.currentX;
            /* 1087 */
            float tmpY = this.currentY;
            /* 1088 */
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
                /* 1089 */
                tmpX += this.f_effect.x;
                /* 1090 */
                tmpY += this.f_effect.y;
                /*      */
            }
            /*      */
            /* 1093 */
            sb.setColor(Color.WHITE);
            /* 1094 */
            sb.draw(this.img, tmpX - 64.0F, tmpY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
            /*      */
        }
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /* 1113 */
        this.hb.render(sb);
        /*      */
    }

    /*      */
    /*      */
    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {
        /* 1117 */
        if (this.counter > -1) {
            /* 1118 */
            if (inTopPanel) {
                /* 1119 */
                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont,
                        /*      */
                        /*      */
                        /* 1122 */             Integer.toString(this.counter), offsetX + this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale, Color.WHITE);
                /*      */
                /*      */
            }
            /*      */
            else {
                /*      */
                /* 1127 */
                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont,
                        /*      */
                        /*      */
                        /* 1130 */             Integer.toString(this.counter), this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale, Color.WHITE);
                /*      */
            }
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void renderOutline(Color c, SpriteBatch sb, boolean inTopPanel) {
        /* 1139 */
        sb.setColor(c);
        /* 1140 */
        if (AbstractDungeon.screen != null && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.NEOW_UNLOCK) {
            /* 1141 */
            sb.draw(this.outlineImg, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * 2.0F +
                    /*      */
                    /*      */
                    /*      */
                    /*      */
                    /*      */
                    /*      */
                    /*      */
                    /* 1149 */           MathUtils.cosDeg((float) (System.currentTimeMillis() / 5L % 360L)) / 15.0F, Settings.scale * 2.0F +
                    /* 1150 */           MathUtils.cosDeg((float) (System.currentTimeMillis() / 5L % 360L)) / 15.0F, this.rotation, 0, 0, 128, 128, false, false);
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
        }
        /* 1159 */
        else if (this.hb.hovered && Settings.isControllerMode) {
            /* 1160 */
            sb.setBlendFunction(770, 1);
            /* 1161 */
            this.goldOutlineColor.a = 0.6F + MathUtils.cosDeg((float) (System.currentTimeMillis() / 2L % 360L)) / 5.0F;
            /* 1162 */
            sb.setColor(this.goldOutlineColor);
            /* 1163 */
            sb.draw(this.outlineImg, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /* 1180 */
            sb.setBlendFunction(770, 771);
            /*      */
        } else {
            /* 1182 */
            sb.draw(this.outlineImg, this.currentX - 64.0F, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void renderOutline(SpriteBatch sb, boolean inTopPanel) {
        /* 1204 */
        float tmpX = this.currentX - 64.0F;
        /* 1205 */
        if (inTopPanel) {
            /* 1206 */
            tmpX += offsetX;
            /*      */
        }
        /*      */
        /* 1209 */
        if (this.hb.hovered && Settings.isControllerMode) {
            /* 1210 */
            sb.setBlendFunction(770, 1);
            /* 1211 */
            this.goldOutlineColor.a = 0.6F + MathUtils.cosDeg((float) (System.currentTimeMillis() / 2L % 360L)) / 5.0F;
            /* 1212 */
            sb.setColor(this.goldOutlineColor);
            /* 1213 */
            sb.draw(this.outlineImg, tmpX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /* 1230 */
            sb.setBlendFunction(770, 771);
            /*      */
        } else {
            /*      */
            /* 1233 */
            sb.setColor(PASSIVE_OUTLINE_COLOR);
            /* 1234 */
            sb.draw(this.outlineImg, tmpX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, this.rotation, 0, 0, 128, 128, false, false);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void renderFlash(SpriteBatch sb, boolean inTopPanel) {
        /* 1255 */
        float tmp = Interpolation.exp10In.apply(0.0F, 4.0F, this.flashTimer / 2.0F);
        /*      */
        /* 1257 */
        sb.setBlendFunction(770, 1);
        /* 1258 */
        this.flashColor.a = this.flashTimer * 0.2F;
        /* 1259 */
        sb.setColor(this.flashColor);
        /* 1260 */
        float tmpX = this.currentX - 64.0F;
        /* 1261 */
        if (inTopPanel) {
            /* 1262 */
            tmpX += offsetX;
            /*      */
        }
        /*      */
        /* 1265 */
        sb.draw(this.img, tmpX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale + tmp, this.scale + tmp, this.rotation, 0, 0, 128, 128, false, false);
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /* 1283 */
        sb.draw(this.img, tmpX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale + tmp * 0.66F, this.scale + tmp * 0.66F, this.rotation, 0, 0, 128, 128, false, false);
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /* 1301 */
        sb.draw(this.img, tmpX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale + tmp / 3.0F, this.scale + tmp / 3.0F, this.rotation, 0, 0, 128, 128, false, false);
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /* 1319 */
        sb.setBlendFunction(770, 771);
        /*      */
    }

    /*      */
    /*      */
    public void beginPulse() {
        /* 1323 */
        this.flashTimer = 1.0F;
        /*      */
    }

    /*      */
    /*      */
    public void beginLongPulse() {
        /* 1327 */
        this.flashTimer = 1.0F;
        /* 1328 */
        this.pulse = true;
        /*      */
    }

    /*      */
    /*      */
    public void stopPulse() {
        /* 1332 */
        this.pulse = false;
        /*      */
    }

    /*      */
    /*      */
    public void flash() {
        /* 1336 */
        this.flashTimer = 2.0F;
        /*      */
    }

    /*      */
    /*      */
    public void renderBossTip(SpriteBatch sb) {
        /* 1340 */
        TipHelper.queuePowerTips(Settings.WIDTH * 0.63F, Settings.HEIGHT * 0.63F, this.tips);
        /*      */
    }

    /*      */
    /*      */
    public void renderTip(SpriteBatch sb) {
        /* 1344 */
        if (InputHelper.mX < 1400.0F * Settings.scale) {
            /* 1345 */
            if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW) {
                /* 1346 */
                TipHelper.queuePowerTips(180.0F * Settings.scale, Settings.HEIGHT * 0.7F, this.tips);
                /* 1347 */
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP && this.tips.size() > 2 &&
                    /* 1348 */         !AbstractDungeon.player.hasRelic(this.relicId)) {
                /* 1349 */
                TipHelper.queuePowerTips(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY + 180.0F * Settings.scale, this.tips);
                /*      */
                /*      */
                /*      */
            }
            /* 1353 */
            else if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(this.relicId)) {
                /* 1354 */
                TipHelper.queuePowerTips(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY - 30.0F * Settings.scale, this.tips);
                /*      */
                /*      */
                /*      */
            }
            /* 1358 */
            else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
                /* 1359 */
                TipHelper.queuePowerTips(360.0F * Settings.scale, InputHelper.mY + 50.0F * Settings.scale, this.tips);
                /*      */
            } else {
                /* 1361 */
                TipHelper.queuePowerTips(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY + 50.0F * Settings.scale, this.tips);
                /*      */
            }
            /*      */
            /*      */
        }
        /*      */
        else {
            /*      */
            /* 1367 */
            TipHelper.queuePowerTips(InputHelper.mX - 350.0F * Settings.scale, InputHelper.mY - 50.0F * Settings.scale, this.tips);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public boolean canPlay(AbstractCard card) {
        /* 1375 */
        return true;
        /*      */
    }

    /*      */
    /*      */
    public static String gameDataUploadHeader() {
        /* 1379 */
        GameDataStringBuilder builder = new GameDataStringBuilder();
        /*      */
        /* 1381 */
        builder.addFieldData("name");
        /* 1382 */
        builder.addFieldData("relicID");
        /* 1383 */
        builder.addFieldData("color");
        /* 1384 */
        builder.addFieldData("description");
        /* 1385 */
        builder.addFieldData("flavorText");
        /* 1386 */
        builder.addFieldData("cost");
        /* 1387 */
        builder.addFieldData("tier");
        /* 1388 */
        builder.addFieldData("assetURL");
        /*      */
        /* 1390 */
        return builder.toString();
        /*      */
    }

    /*      */
    /*      */
    protected void initializeTips() {
        /* 1394 */
        Scanner desc = new Scanner(this.description);
        /*      */
        /* 1396 */
        while (desc.hasNext()) {
            /* 1397 */
            String s = desc.next();
            /* 1398 */
            if (s.charAt(0) == '#') {
                /* 1399 */
                s = s.substring(2);
                /*      */
            }
            /*      */
            /* 1402 */
            s = s.replace(',', ' ');
            /* 1403 */
            s = s.replace('.', ' ');
            /* 1404 */
            s = s.trim();
            /* 1405 */
            s = s.toLowerCase();
            /*      */
            /* 1407 */
            boolean alreadyExists = false;
            /* 1408 */
            if (GameDictionary.keywords.containsKey(s)) {
                /* 1409 */
                s = (String) GameDictionary.parentWord.get(s);
                /* 1410 */
                for (PowerTip t : this.tips) {
                    /* 1411 */
                    if (t.header.toLowerCase().equals(s)) {
                        /* 1412 */
                        alreadyExists = true;
                        /*      */
                        break;
                        /*      */
                    }
                    /*      */
                }
                /* 1416 */
                if (!alreadyExists) {
                    /* 1417 */
                    this.tips.add(new PowerTip(TipHelper.capitalize(s), (String) GameDictionary.keywords.get(s)));
                    /*      */
                }
                /*      */
            }
            /*      */
        }
        /*      */
        /* 1422 */
        desc.close();
        /*      */
    }

    /*      */
    /*      */
    public String gameDataUploadData(String color) {
        /* 1426 */
        GameDataStringBuilder builder = new GameDataStringBuilder();
        /*      */
        /* 1428 */
        builder.addFieldData(this.name);
        /* 1429 */
        builder.addFieldData(this.relicId);
        /* 1430 */
        builder.addFieldData(color);
        /* 1431 */
        builder.addFieldData(this.description);
        /* 1432 */
        builder.addFieldData(this.flavorText);
        /* 1433 */
        builder.addFieldData(this.cost);
        /* 1434 */
        builder.addFieldData(this.tier.name());
        /* 1435 */
        builder.addFieldData(this.assetURL);
        /*      */
        /* 1437 */
        return builder.toString();
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public String toString() {
        /* 1444 */
        return this.name;
        /*      */
    }

    /*      */
    /*      */
    /*      */
    public int compareTo(AbstractRelic arg0) {
        /* 1449 */
        return this.name.compareTo(arg0.name);
        /*      */
    }

    /*      */
    /*      */
    public String getAssetURL() {
        /* 1453 */
        return this.assetURL;
        /*      */
    }

    /*      */
    /*      */
    public HashMap<String, Serializable> getLocStrings() {
        /* 1457 */
        HashMap<String, Serializable> relicData = new HashMap<>();
        /* 1458 */
        relicData.put("name", this.name);
        /* 1459 */
        relicData.put("description", this.description);
        /* 1460 */
        return relicData;
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public boolean canSpawn() {
        /* 1469 */
        return true;
        /*      */
    }

    /*      */
    /*      */
    /*      */
    public void onUsePotion() {
    }

    /*      */
    /*      */
    /*      */
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
    }

    /*      */
    /*      */
    /*      */
    public void onLoseHp(int damageAmount) {
    }

    /*      */
    /*      */
    public static void updateOffsetX() {
        /* 1482 */
        float target = (-relicPage * Settings.WIDTH) + relicPage * (PAD_X + 36.0F * Settings.scale);
        /* 1483 */
        if (AbstractDungeon.player.relics.size() >= 26) {
            /* 1484 */
            target += 36.0F * Settings.scale;
            /*      */
        }
        /*      */
        /* 1487 */
        if (offsetX != target) {
            /* 1488 */
            offsetX = MathHelper.uiLerpSnap(offsetX, target);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    public void loadLargeImg() {
        /* 1493 */
        if (this.largeImg == null) {
            /* 1494 */
            this.largeImg = ImageMaster.loadImage("images/largeRelics/" + this.imgUrl);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    protected void addToBot(AbstractGameAction action) {
        /* 1499 */
        AbstractDungeon.actionManager.addToBottom(action);
        /*      */
    }

    /*      */
    /*      */
    protected void addToTop(AbstractGameAction action) {
        /* 1503 */
        AbstractDungeon.actionManager.addToTop(action);
        /*      */
    }

    /*      */
    /*      */
    public int onLoseHpLast(int damageAmount) {
        /* 1507 */
        return damageAmount;
        /*      */
    }

    /*      */
    /*      */
    public void wasHPLost(int damageAmount) {
    }

    /*      */
    /*      */
    public abstract AbstractRing makeCopy();
    /*      */
}

