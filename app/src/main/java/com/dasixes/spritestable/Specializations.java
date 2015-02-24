package com.dasixes.spritestable;

/**
 * Created by cdavis on 2/12/2015.
 */
public class Specializations {

        private long id;
        private String SpecializationName = "";
        private Integer LinkedSkill = -1;
        private Boolean Exists = false;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setSpecializationName(String Name) {
            SpecializationName=Name;
        }

        public String getSpecializationName() {
            return SpecializationName;
        }

        public Long getLinkedSkill() {
            return new Long(this.LinkedSkill);
        }

        public void setLinkedSkill(Integer Skill){
            LinkedSkill=Skill;
        }

        public void setExists(Boolean DoesItExist){
            Exists=DoesItExist;
        }
    public Boolean getExists(){
        return Exists;
    }

}
