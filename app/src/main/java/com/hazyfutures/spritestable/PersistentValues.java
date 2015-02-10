package com.hazyfutures.spritestable;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdavis on 1/22/2015.
 * Values that should be set to persist between activities
 */
public class PersistentValues {
    Context context;
    public int pvBody = 0;
    public int pvIntuition = 0;
    public int pvWillpower = 0;
    public int pvLogic = 0;
    public int pvCharisma = 0;
    public int pvStun = 0;
    public int pvPhysical = 0;
    public int pvKarma = 0;
    public int pvKarmaUsed = 0;
    public int pvResonance = 0;
    public int pvCompiling = 0;
    public int pvRegistering = 0;
    public int pvHoursThisSession = 0;
    public int pvSleeplessHours = 0;
    public int pvHoursSinceKarmaRefresh = 0;
    public int pvConsecutiveRest = 0;

    public int pvActiveSpriteId = 0;
    public List<String> pvSpriteList = new ArrayList<String>();
    List<Sprite> pvSprites = new ArrayList<>();
    private StatsDataSource datasource;


    public void SaveStatToDB(String stat, Integer value) {
        datasource.updateStat(stat, value);
    }

    public void SaveSpriteToDB() {
        SaveSpriteToDB(pvSprites.get(pvActiveSpriteId));
    }

    public void SaveSpriteToDB(Sprite SpriteToSave){
        datasource.updateSprite(SpriteToSave);
    }


    public void SaveAllToDB() {
        datasource.updateStat("Stun", pvStun);
        datasource.updateStat("Physical", pvPhysical);
        datasource.updateStat("Karma", pvKarma);
        datasource.updateStat("KarmaUsed", pvKarmaUsed);
        datasource.updateStat("Body", pvBody);
        datasource.updateStat("Intuition", pvIntuition);
        datasource.updateStat("Willpower", pvWillpower);
        datasource.updateStat("Logic", pvLogic);
        datasource.updateStat("Charisma", pvCharisma);
        datasource.updateStat("Resonance", pvResonance);
        datasource.updateStat("Compiling", pvCompiling);
        datasource.updateStat("Registering", pvRegistering);
        datasource.updateStat("Hours", pvHoursThisSession);
        datasource.updateStat("SleeplessHours", pvSleeplessHours);
        datasource.updateStat("ConsecutiveRest", pvConsecutiveRest);
        datasource.updateStat("ActiveSpriteId", pvActiveSpriteId);
        datasource.updateStat("HoursSinceKarmaRefresh", pvHoursSinceKarmaRefresh);
        datasource.updateSprite(pvSprites.get(pvActiveSpriteId));
        datasource.updateStat("CodeSlinger", CodeSlinger);
        datasource.updateStat("FocusedConcentration", FocusedConcentration);
        datasource.updateStat("HighPainTolerance", HighPainTolerance);
        datasource.updateStat("HomeGround", HomeGround);
        datasource.updateStat("NaturalHardening", NaturalHardening);
        datasource.updateStat("QuickHealer", QuickHealer);
        datasource.updateStat("Toughness", Toughness);
        datasource.updateStat("WillToLive", WillToLive);
        datasource.updateStat("BadLuck", BadLuck);
        datasource.updateStat("CodeBlock", CodeBlock);
        datasource.updateStat("LossOfConfidence", LossOfConfidence);
        datasource.updateStat("LowPainTolerance", LowPainTolerance);
        datasource.updateStat("SensitiveSystem", SensitiveSystem);
        datasource.updateStat("SimsenseVertigo", SimsenseVertigo);
        datasource.updateStat("SlowHealer", SlowHealer);
        datasource.updateStat("Perceptive", Perceptive);
        datasource.updateStat("ToughAsNailsPhysical", ToughAsNailsPhysical);
        datasource.updateStat("ToughAsNailsStun", ToughAsNailsStun);
        datasource.updateStat("Asthma", Asthma);
        datasource.updateStat("AsthmaFatigueDamage", AsthmaFatigueDamage);
        datasource.updateStat("BiPolar", BiPolar);
        datasource.updateStat("BiPolarCurrent", BiPolarCurrent);
        datasource.updateStat("Blind", Blind);
        datasource.updateStat("ComputerIlliterate", ComputerIlliterate);
        datasource.updateStat("Deaf", Deaf);
        datasource.updateStat("DimmerBulb", DimmerBulb);
        datasource.updateStat("Illiterate", Illiterate);
        datasource.updateStat("Oblivious", Oblivious);
        datasource.updateStat("PieIesuDomine", PieIesuDomine);

    }

    public void RestoreFromDB(Context content) {


        datasource = new StatsDataSource(content);
        datasource.open();
        List<Stat> values = datasource.getAllStats();
        pvSprites = datasource.getAllSprites();
        pvActiveSpriteId = 0;

        String stat;
        Integer value;
        for (int i = 0; i < values.size(); ++i) {
            stat = values.get(i).getStat();
            value = values.get(i).getValue();
            switch (stat) {
                case "Body":
                    pvBody = value;
                    break;
                case "Willpower":
                    pvWillpower = value;
                    break;
                case "Intuition":
                    pvIntuition = value;
                    break;
                case "Logic":
                    pvLogic = value;
                    break;
                case "Charisma":
                    pvCharisma = value;
                    break;
                case "Compiling":
                    pvCompiling = value;
                    break;
                case "Registering":
                    pvRegistering = value;
                    break;
                case "Resonance":
                    pvResonance = value;
                    break;
                case "Stun":
                    pvStun = value;
                    break;
                case "Physical":
                    pvPhysical = value;
                    break;
                case "Karma":
                    pvKarma = value;
                    break;
                case "KarmaUsed":
                    pvKarmaUsed = value;
                    break;
                case "HoursThisSession":
                    pvHoursThisSession = value;
                    break;
                case "SleeplessHours":
                    pvSleeplessHours= value;
                    break;
                case "ConsecutiveRest":
                    pvConsecutiveRest= value;
                    break;
                case "HoursSinceKarmaRefresh":
                    pvConsecutiveRest= value;
                    break;
                case "CodeSlinger":
                    CodeSlinger = value;
                    break;
                case "FocusedConcentration":
                    FocusedConcentration = value;
                    break;
                case "HighPainTolerance":
                    HighPainTolerance = value;
                    break;
                case "HomeGround":
                    HomeGround = value;
                    break;
                case "NaturalHardening":
                    NaturalHardening = value;
                    break;
                case "QuickHealer":
                    QuickHealer = value;
                    break;
                case "Toughness":
                    Toughness = value;
                    break;
                case "WillToLive":
                    WillToLive = value;
                    break;
                case "BadLuck":
                    BadLuck = value;
                    break;
                case "CodeBlock":
                    CodeBlock = value;
                    break;
                case "LossOfConfidence":
                    LossOfConfidence = value;
                    break;
                case "LowPainTolerance":
                    LowPainTolerance = value;
                    break;
                case "SensitiveSystem":
                    SensitiveSystem = value;
                    break;
                case "SimsenseVertigo":
                    SimsenseVertigo= value;
                    break;
                case "SlowHealer":
                    SlowHealer= value;
                    break;
                case "Perceptive":
                    Perceptive= value;
                    break;
                case "ToughAsNailsPhysical":
                    ToughAsNailsPhysical = value;
                    break;
                case "ToughAsNailsStun":
                    ToughAsNailsStun = value;
                    break;
                case "Asthma":
                    Asthma = value;
                    break;
                case "AsthmaFatigueDamage":
                    AsthmaFatigueDamage = value;
                    break;
                case "BiPolar":
                    BiPolar = value;
                    break;
                case "BiPolarCurrent":
                    BiPolarCurrent = value;
                    break;
                case "Blind":
                    Blind = value;
                    break;
                case "ComputerIlliterate":
                    ComputerIlliterate = value;
                    break;
                case "Deaf":
                    Deaf = value;
                    break;
                case "DimmerBulb":
                    DimmerBulb = value;
                    break;
                case "Illiterate":
                    Illiterate= value;
                    break;
                case "Oblivious":
                    Oblivious= value;
                    break;
                case "PieIesuDomine":
                    PieIesuDomine= value;
                    break;

            }

        }
    }

    public Sprite getCurrentSprite() {
        if (pvSprites.isEmpty()) {
            Sprite newSprite = new Sprite();
            newSprite.setSpriteType(1);
            newSprite.setRating(1);
            newSprite.setRegistered(0);
            pvSprites.add(newSprite);
            pvActiveSpriteId = 0;
        }

        return pvSprites.get(pvActiveSpriteId);

    }
    public Sprite getSprite(long id) {

        return pvSprites.get(pvActiveSpriteId);

    }

    public void DeleteSprite(Sprite SpriteToDie){
        datasource.deleteSprite(SpriteToDie);
    }


    public void UpdateSpriteList(){
        boolean unregisteredExists=false;
        pvSpriteList.clear();
        List<Sprite> Unnecessary = new ArrayList<>();
        for(Sprite sprite : pvSprites){

            //Keep the first unregistered sprite
            //Delete any other unregistered sprites
            //If no unregistered sprites found, create a NEW SPRITE
            //If sprite is Registered and Services==0 delete it
            //Add it to the list if it's registered and services>0 or if it's the first unregistered services>0

            if((sprite.getRegistered()==0&&unregisteredExists)||(sprite.getRegistered()==1&&sprite.getServicesOwed()==0)){ //Second or later unregistered item, or all services used
                Unnecessary.add(sprite);        //Set up to remove it from the list
                if(pvSprites.get(pvActiveSpriteId)==sprite){      //If we're deleting the active sprite then aim the sprite pointer at an existing sprite.
                    if(pvActiveSpriteId>0){pvActiveSpriteId--;}
                }
            }else{
                String title = String.valueOf("Force " + sprite.getRating() + " " + sprite.getType()) + " with " + sprite.getServicesOwed() + " services";

                if(sprite.getRegistered()==1){
                    title=title + " Registered";
                }else{
                    unregisteredExists=true;
                }
                pvSpriteList.add(title);
            }
        }
        for(Sprite deleteSprite : Unnecessary){
            datasource.deleteSprite(deleteSprite);
        }
        pvSprites.removeAll(Unnecessary);

        if(!unregisteredExists){//Creating a new Sprite is an option
            Sprite newSprite= new Sprite();
            newSprite.setRating(getCurrentSprite().getRating());
            newSprite.setSpriteType(getCurrentSprite().getType());
            pvSprites.add(newSprite);
            //Populate Sprite List with NEW option
            String title = "NEW SPRITE";
            pvSpriteList.add(title);
            pvSprites.get(pvSpriteList.size()-1).setId(datasource.insertSprite(pvSprites.get(pvSpriteList.size()-1)));  //Save the new sprite to the DB
        }

        //SaveAllToDB();

    }

 /*   public void nextSprite() {
        if (pvActiveSpriteId < (pvSprites.size() - 1)) {
            pvActiveSpriteId++;
        } else {
            pvActiveSpriteId = 0;
        }
    }
*/

    //Codeslinger: +2 dice a specific matrix action
    Integer CodeSlinger=0;
    public Integer getCodeSlinger(){
        return CodeSlinger;
    }
    public void setCodeSlinger(Integer value){
        CodeSlinger=value;
    }
    public Integer getCodeSlingerMod(Integer MatrixAction){
        if(CodeSlinger==MatrixAction){
            return 2;
        }
        return 0;
    }
    //TODO Decide on standardized ID's for matrix actions
//Focused concentration 1-6: May sustain Rating force of without negative penalties
    Integer FocusedConcentration=0;
    public Integer getFocusedConcentration(){
        return FocusedConcentration;
    }
    public void setFocusedConcentration(Integer value){
        FocusedConcentration=value;
    }
    //High paint tolerance 1-3: +1 rating boxes of stun/physical before penalties are incurred
    Integer HighPainTolerance=0;
    public Integer getHighPainTolerance(){
        return HighPainTolerance;
    }
    public void setHighPainTolerance(Integer value){
        HighPainTolerance=value;
    }
    //Home ground: +2 bonus to matrix tests
    Integer HomeGround =0;
    public Integer getHomeGround(){
        return HomeGround;
    }
    public void setHomeGround(Integer value){
        HomeGround=value;
    }

    //Natural Hardening: +1 biofeedback filtering
    Integer NaturalHardening=0;
    public Integer getNaturalHardening(){
        return NaturalHardening;
    }
    public void setNaturalHardening(Integer value){
        NaturalHardening=value;
    }
    //Quick Healer: +2 dice pool on healing tests
    Integer QuickHealer=0;
    public Integer getQuickHealer(){
        return QuickHealer;
    }
    public void setQuickHealer(Integer value){
        QuickHealer=value;
    }
    //Toughness: +1 die damage resistance tests
    Integer Toughness=0;
    public Integer getToughness(){
        return Toughness;
    }
    public void setToughness(Integer value){
        Toughness=value;
    }
    //Will to live 1-3: +Rating in overflow boxes
    Integer WillToLive=0;
    public Integer getWillToLive(){
        return WillToLive;
    }
    public void setWillToLive(Integer value){
        WillToLive=value;
    }
    //Bad Luck: d6 when edge used, on a 1 opposite of effect occurs, only happens once per session
    Integer BadLuck=0;
    public Integer getBadLuck(){
        return BadLuck;
    }
    public void setBadLuck(Integer value){
        BadLuck=value;
    }
    public Integer RollBadLuck(Integer Dice, Integer Edge){
        //TODO Roll dice, and include option for bad luck to happen
        return -1;
    }
    //Codeblock -2 dice pool on a specific action
    Integer CodeBlock=0;
    public Integer getCodeBlock(){
        return CodeBlock;
    }
    public void setCodeBlock(Integer value){
        CodeBlock=value;
    }
    public Integer getCodeBlockMod(Integer MatrixAction){
        if(CodeSlinger==MatrixAction){
            return -2;
        }
        return 0;
    }
    //Loss of Confidence:  -2 dice on a skill
    //TODO: Add List of Skills with ID's
    Integer LossOfConfidence=0;
    public Integer getLossOfConfidence(){
        return LossOfConfidence;
    }
    public void setLossOfConfidence(Integer value){
        LossOfConfidence=value;
    }
    public Integer getLossOfConfidenceMod(Integer SkillID){
        if(LossOfConfidence==SkillID){
            return -2;
        }
        return 0;
    }
    //Low pain tolerance: Modifier every 2 boxes of damage, not 3
    Integer LowPainTolerance=0;
    public Integer getLowPainTolerance(){
        return LowPainTolerance;
    }
    public void setLowPainTolerance(Integer value){
        LowPainTolerance=value;
    }
    //Sensitive system: Willpower(2) test before fading tests, +2 fading if it fails
    Integer SensitiveSystem=0;
    public Integer getSensitiveSystem(){
        return SensitiveSystem;
    }
    public void setSensitiveSystem(Integer value){
        SensitiveSystem=value;
    }
    public Integer getSensitiveSystemMod(Integer Willpower){
        if(SensitiveSystem>0){
            //TODO Willpower (2) test
            return 2;
        }
        return 0;
    }
    //Simsense Vertigo: -2 dice all matrix tests
    Integer SimsenseVertigo=0;
    public Integer getSimsenseVertigo(){
        return SimsenseVertigo;
    }
    public void setSimsenseVertigo(Integer value){
        SimsenseVertigo=value;
    }
    //Slow Healer: -2 dice on healing tests
    Integer SlowHealer=0;
    public Integer getSlowHealer(){
        return SlowHealer;
    }
    public void setSlowHealer(Integer value){
        SlowHealer=value;
    }
    //Perceptive 1-2: +Rating die matrix perception test
    Integer Perceptive=0;
    public Integer getPerceptive(){
        return Perceptive;
    }
    public void setPerceptive(Integer value){
        Perceptive=value;
    }
    //Spike Resistance 1-3: +rating vs. biofeedback
    Integer SpikeResistance=0;
    public Integer getSpikeResistance(){
        return SpikeResistance;
    }
    public void setSpikeResistance(Integer value){
        SpikeResistance=value;
    }
    //Tough as Nails 1-4 Stun/Physical: +1 box of either stun or physical per rating, up to 3 on either.
    Integer ToughAsNailsPhysical=0;
    public Integer getToughAsNailsPhysical(){
        return ToughAsNailsPhysical;
    }
    public void setToughAsNailsPhysical(Integer value){
        ToughAsNailsPhysical=value;
    }
    Integer ToughAsNailsStun=0;
    public Integer getToughAsNailsStun(){
        return ToughAsNailsStun;
    }
    public void setToughAsNailsStun(Integer value){
        ToughAsNailsStun=value;
    }
    //Asthma: Fatigue damage twice as often, 2 boxes fatigue damage=-1 all actions, 4 boxes=further fatigue damage only resisted by willpower, 8 boxes=additional -1 penatly all actions
    Integer Asthma=0;
    Integer AsthmaFatigueDamage=0;
    public Integer getAsthma(){
        return Asthma;
    }
    public void setAsthma(Integer value){
        Asthma=value;
    }
    public Integer getAsthmaFatigueDamage(){
        return AsthmaFatigueDamage;
    }
    public void setAsthmaFatigueDamage(Integer value){
        AsthmaFatigueDamage=value;
    }

    //Bi-polar: Once per day at sleep time roll die, 1-2 Depressed, 3-4 Manic, 5-6Stable, Medication available.  Not taking for 12 hours results in roll
    Integer BiPolar=0;
    public Integer getBiPolar(){
        return BiPolar;
    }
    public void setBiPolar(Integer value){
        BiPolar=value;
    }
    Integer BiPolarCurrent=6;
    public Integer getBiPolarCurrent(){
        return BiPolarCurrent;
    }
    public void setBiPolarCurrent(Integer value){
        BiPolarCurrent=value;
    }
    public void BiPolarRoll(){
        //Random Change to status
    }
    public Integer getBiPolarMod(String stat){
//Results: Manic: -2 logic + intuition rolls, Depressive -2 logic, intuition, Stable no penalties
        return -1;
    }


    //Blind: -4 perception tests
    Integer Blind=0;
    public Integer getBlind(){
        return Blind;
    }
    public void setBlind(Integer value){
        Blind=value;
    }
    //Computer illiterate: -4 all matrix tests
    Integer ComputerIlliterate=0;
    public Integer getComputerIlliterate(){
        return ComputerIlliterate;
    }
    public void setComputerIlliterate(Integer value){
        ComputerIlliterate=value;
    }
    //Deaf: -2 perception tests
    Integer Deaf=0;
    public Integer getDeaf(){
        return Deaf;
    }
    public void setDeaf(Integer value){
        Deaf=value;
    }
    //Dimmer bulb: -1 logic + intuition tests
    Integer DimmerBulb=0;
    public Integer getDimmerBulb(){
        return DimmerBulb;
    }
    public void setDimmerBulb(Integer value){
        DimmerBulb=value;
    }
    //Illiterate: -2 matrix tests
    Integer Illiterate=0;
    public Integer getIlliterate(){
        return Illiterate;
    }
    public void setIlliterate(Integer value){
        Illiterate=value;
    }
    //Oblivious 1-2: (1) -2 perception tests, (2) +1 threshold perception tests
    Integer Oblivious=0;
    public Integer getOblivious(){
        return Oblivious;
    }
    public void setOblivious(Integer value){
        Oblivious=value;
    }
    //Pie Iesu Domine: High pain tolerance 1, Always has one box physical damage
    Integer PieIesuDomine=0;
    public Integer getPieIesuDomine(){
        return PieIesuDomine;
    }
    public void setPieIesuDomine(Integer value){
        PieIesuDomine=value;
    }


    public void LoadQualities(Context context){
        //Load from the Database
    }

}



