package chess;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TileTest.class, BoardTest.class, PlayerBlackTest.class, PlayerWhiteTest.class,  BishopTest.class, KingTest.class, KnightTest.class, PawnTest.class, QueenTest.class, RookTest.class, AlfilTest.class, GameTest.class})
public class ChessTests {
}
