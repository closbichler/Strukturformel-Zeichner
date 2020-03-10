public enum GreekNumbers {
    //Used for storing the greekNumbers for multiple bonds e.g. dien
    none(0),
    di(2), tri(3), tetra(4), penta(5),
    hexa(6), hepta(7), okta(8), enna(9),
    deca(10), hendeka(11), dodeka(12);

    //GreekNumbers found on
    // https://de.wikipedia.org/wiki/Griechische_Zahlw%C3%B6rter#Altgriechische_Zahlw%C3%B6rter

    //Stores the number behind the greek number word
    private int multiple_bondings_count;


    GreekNumbers(int multiple_bondings_count) {
        this.multiple_bondings_count = multiple_bondings_count;
    }

    public int getValue() {
        return multiple_bondings_count;
    }
}
