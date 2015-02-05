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
        datasource.updateSprite(pvSprites.get(pvActiveSpriteId));
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
    }

    public void RestoreFromDB(Context content) {
        this.context = content;
        datasource = new StatsDataSource(this.context);
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

    public void UpdateSprite(Sprite SpriteToSave){
        datasource.updateSprite(SpriteToSave);
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
            datasource.updateSprite(pvSprites.get(pvSpriteList.size()-1));  //Save the new sprite to the DB
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

}



