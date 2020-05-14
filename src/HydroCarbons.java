public enum HydroCarbons {
    //Used for storing the Carbon Count of the HydroCarbon
    none(0),
    meth(1), eth(2), prop(3),
    but(4), pent(5), hex(6),
    hept(7), oct(8), non(9),
    dec(10), undec(11), dodec(12),
    tridec(13), tetradec(14), pentadec(15),
    hexadec(16), heptadec(17), octadec(18),
    nonadec(19), eicos(20), heneicos(21),
    docos(22), tricos(23), tetracos(24),
    pentacos(25), hexacos(26), heptacos(27),
    octacos(28), nonacos(29), tricont(30),
    tetracont(40), pentacont(50), hexacont(60),
    heptacont(70), octacont(80), nonacont(90),
    hect(100), dict(200), trict(300);


    //Found those Names for HydroCarbons from 1 to 30 on the website
    // https://www.internetchemie.info/chemie-lexikon/stoffgruppen/a/alkane.php

    //Storing the carbon amount as integer
    private int carbon_count;

    HydroCarbons(int carbon_count) {
        this.carbon_count = carbon_count;
    }

    public int getValue() {
        return carbon_count;
    }
}
