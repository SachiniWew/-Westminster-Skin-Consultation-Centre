package Classes;

public enum Specialisation {
    Acne(1),
    Blackheads(2),
    Eczema_Dermatitis(3),
    Ringworm_infections(4),
    Nail_infections(5),
    Scalp_infections(6),
    Skin_tags(7),
    Herpes(8),
    Psoriasis(9),
    Vitiligo(10),
    Hives(11),
    Acanthosis_nigricans(12),
    Diaper_rash(13),
    Dry_skin(14),
    Head_lice(15),
    Lichen_planus(16),
    Lupus(17),
    Shingles(18),
    Skin_cancers(19);

    private final int c;
    Specialisation(int i){
        c = i;
    }

    public int getNumberOfSpecialisation(){
        return c;
    }
    public Specialisation getSpecialisationOfNumber(int c){
        for (Specialisation e :
                Specialisation.values()) {
            if (c == e.getNumberOfSpecialisation()) {
                return e;
            }
        }

        return null;
    }
}
