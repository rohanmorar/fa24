package bomb;

import common.IntList;

public class BombMain {
    public static void answers(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct inputs (passwords) to each phase using debugging techniques
        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0("39291226");
        }
        if (phase >= 1) {
            IntList nt = new IntList(8,null);
            IntList n3 = new IntList(0,nt);
            IntList n2 = new IntList(3,n3);
            IntList n1 = new IntList(9,n2);
            IntList nh = new IntList(0,n1);
            b.phase1(nh); // Figure this out too
        }
        if (phase >= 2) {
            b.phase2("793227803");
        }
    }
}
