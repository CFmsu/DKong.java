package Util;

public class Constant {

    public static class PlayerConstants{

        public static class Directions{
            public static final int LEFT = 0;
            public static final int RIGHT = 1;
            public static final int UP = 2;
            public static final int DOWN = 3;

        }

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int CLIMBING = 2;
        public static final int JUMPING = 3;
        public static final int HAMMERJUMPING = 4;
        public static final int HAMMER = 5;
        public static final int DYING = 6;

        public static int getAnimAmount(int action) {

            switch (action){

                case IDLE, JUMPING:
                    return 1;
                case RUNNING:
                    return 3;
                case CLIMBING, DYING:
                    return 4;
                case HAMMERJUMPING:
                    return 5;
                case HAMMER:
                    return 2;
            }
            return 1;
        }
        }
}
