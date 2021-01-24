package ca.utoronto.utm.assignment1.othellotest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.utoronto.utm.assignment1.othello.Move;
import ca.utoronto.utm.assignment1.othello.Othello;
import ca.utoronto.utm.assignment1.othello.OthelloBoard;


public class OthelloBoardTest {
	OthelloBoard board;
	Move[] moves = { new Move(2, 4), new Move(2, 5), new Move(2, 6), new Move(2, 3), new Move(2, 2), new Move(3, 2),
			new Move(4, 2), new Move(5, 4), new Move(6, 4) };
	
	@Before
	public void setUp() throws Exception {
		board=new OthelloBoard(Othello.DIMENSION); // 8x8 board
		board.move(2, 4, OthelloBoard.P1);
		board.move(2, 5, OthelloBoard.P2);
		board.move(2, 6, OthelloBoard.P1);
		board.move(2, 3, OthelloBoard.P2);
		
		// Board now looks like
		//   0 1 2 3 4 5 6 7
		//  +-+-+-+-+-+-+-+-+
		// 0| | | | | | | | |0
		//  +-+-+-+-+-+-+-+-+
		// 1| | | | | | | | |1
		//  +-+-+-+-+-+-+-+-+
		// 2| | | |O|X|X|X| |2
		//  +-+-+-+-+-+-+-+-+
		// 3| | | |O|O| | | |3
		//  +-+-+-+-+-+-+-+-+
		// 4| | | |O|X| | | |4
		//  +-+-+-+-+-+-+-+-+
		// 5| | | | | | | | |5
		//  +-+-+-+-+-+-+-+-+
		// 6| | | | | | | | |6
		// +-+-+-+-+-+-+-+-+
		// 7| | | | | | | | |7
		//  +-+-+-+-+-+-+-+-+
		//   0 1 2 3 4 5 6 7
		//
		// X:4 O:4  X moves next
		// row: col: X makes move (2,2)
		//
		//   0 1 2 3 4 5 6 7
		//  +-+-+-+-+-+-+-+-+
		// 0| | | | | | | | |0
		//  +-+-+-+-+-+-+-+-+
		// 1| | | | | | | | |1
		//  +-+-+-+-+-+-+-+-+
		// 2| | |X|X|X|X|X| |2
		//  +-+-+-+-+-+-+-+-+
		// 3| | | |X|O| | | |3
		//  +-+-+-+-+-+-+-+-+
		// 4| | | |O|X| | | |4
		//  +-+-+-+-+-+-+-+-+
		// 5| | | | | | | | |5
		//  +-+-+-+-+-+-+-+-+
		// 6| | | | | | | | |6
		//  +-+-+-+-+-+-+-+-+
		// 7| | | | | | | | |7
		//  +-+-+-+-+-+-+-+-+
		//   0 1 2 3 4 5 6 7
		//
		// X:7 O:2  O moves next

	}

	@Test
	public void testOthelloBoard() {
		OthelloBoard b=new OthelloBoard(Othello.DIMENSION);
		// Check initial position
		assertEquals("Initial tokens P1",b.get(3, 3), OthelloBoard.P1);
		assertEquals("Initial tokens P1",b.get(4, 4), OthelloBoard.P1);
		assertEquals("Initial tokens P2",b.get(3, 4), OthelloBoard.P2);
		assertEquals("Initial tokens P2",b.get(4, 3), OthelloBoard.P2);
		
		for(int row=0;row<Othello.DIMENSION;row++) {
			for(int col=0;col<Othello.DIMENSION;col++) {
				if((row==3 || row==4) && (col==3 ||col==4))continue;
				assertEquals("Initial empty spots",b.get(row, col),OthelloBoard.EMPTY);
			}
		}
		
	}

	@Test
	public void testGet() {
		// initialized board to OthelloBoard.DIMENSION
		int dim=Othello.DIMENSION;
		OthelloBoard b=new OthelloBoard(Othello.DIMENSION);

		assertEquals("Off board get",b.get(-7,12), OthelloBoard.EMPTY);
		assertEquals("Off board get",b.get(3,11), OthelloBoard.EMPTY);
		assertEquals("Off board get",b.get(11,3), OthelloBoard.EMPTY);
		assertEquals("Off board get",b.get(22,22), OthelloBoard.EMPTY);
		assertEquals("Off board get",b.get(0,dim), OthelloBoard.EMPTY);
		assertEquals("Off board get",b.get(dim,0), OthelloBoard.EMPTY);
		
		assertEquals("On board get P1",b.get(3, 3), OthelloBoard.P1);
		assertEquals("On board get P1",b.get(4, 4), OthelloBoard.P1);
		assertEquals("On board get P2",b.get(3, 4), OthelloBoard.P2);
		assertEquals("On board get P2",b.get(4, 3), OthelloBoard.P2);
	}

	@Test
	public void testGetDimension() {
		assertEquals(board.getDimension(),Othello.DIMENSION);
	}

	@Test
	public void testOtherPlayer() {
		assertEquals(OthelloBoard.otherPlayer(OthelloBoard.P1),OthelloBoard.P2 );
		assertEquals(OthelloBoard.otherPlayer(OthelloBoard.P2),OthelloBoard.P1 );
		assertEquals(OthelloBoard.otherPlayer(OthelloBoard.EMPTY),OthelloBoard.EMPTY );
	}

	@Test
	public void testHasMove() {
		assertEquals(board.hasMove(),OthelloBoard.BOTH);
	}

	@Test
	public void testMove() {
		// Check the scenario we setup in board...
		//   0 1 2 3 4 5 6 7
		//  +-+-+-+-+-+-+-+-+
		// 2| | | |O|X|X|X| |2
		//  +-+-+-+-+-+-+-+-+
		// 3| | | |O|O| | | |3
		//  +-+-+-+-+-+-+-+-+
		// 4| | | |O|X| | | |4
		//  +-+-+-+-+-+-+-+-+
		//   0 1 2 3 4 5 6 7
		//
		// X:4 O:4  X moves next
		assertEquals(board.get(2,0), OthelloBoard.EMPTY);
		assertEquals(board.get(2,1), OthelloBoard.EMPTY);
		assertEquals(board.get(2,2), OthelloBoard.EMPTY);
		assertEquals(board.get(2,3), OthelloBoard.P2);
		assertEquals(board.get(2,4), OthelloBoard.P1);
		assertEquals(board.get(2,5), OthelloBoard.P1);
		assertEquals(board.get(2,6), OthelloBoard.P1);
		assertEquals(board.get(2,7), OthelloBoard.EMPTY);
		
		String beforeMove=board.toString(); // To verify that the board has not changed
		assertFalse("bad move spot occupied", board.move(2, 3, OthelloBoard.P1));
		assertTrue("board unchanged for bad move",beforeMove.equals(board.toString())); 
		assertFalse("bad move spot occupied", board.move(2, 4, OthelloBoard.P1));
		assertTrue("board unchanged for bad move",beforeMove.equals(board.toString())); 
	
		assertFalse("bad move no neighbours", board.move(4, 0, OthelloBoard.P1));
		assertTrue("board unchanged for bad move",beforeMove.equals(board.toString())); 
		assertFalse("bad move no flips", board.move(3, 5, OthelloBoard.P1));
		assertTrue("board unchanged for bad move",beforeMove.equals(board.toString())); 
		
		// row: col: X makes move (2,2)
		assertTrue(board.move(2, 2, OthelloBoard.P1));
		//
		//   0 1 2 3 4 5 6 7
		//  +-+-+-+-+-+-+-+-+
		// 2| | |X|X|X|X|X| |2
		//  +-+-+-+-+-+-+-+-+
		// 3| | | |X|O| | | |3
		//  +-+-+-+-+-+-+-+-+
		// 4| | | |O|X| | | |4	
		//  +-+-+-+-+-+-+-+-+
		//   0 1 2 3 4 5 6 7

		assertEquals(board.get(2,0), OthelloBoard.EMPTY);
		assertEquals(board.get(2,1), OthelloBoard.EMPTY);
		assertEquals(board.get(2,2), OthelloBoard.P1);
		assertEquals(board.get(2,3), OthelloBoard.P1);
		assertEquals(board.get(2,4), OthelloBoard.P1);
		assertEquals(board.get(2,5), OthelloBoard.P1);
		assertEquals(board.get(2,6), OthelloBoard.P1);
		assertEquals(board.get(2,7), OthelloBoard.EMPTY);
		
	}

	@Test
	public void testGetCount() {
		assertEquals("counting P1",board.getCount(OthelloBoard.P1),4);
		assertEquals("counting P2",board.getCount(OthelloBoard.P2),4);
		board.move(2, 2, OthelloBoard.P1);
		assertEquals("counting P1",board.getCount(OthelloBoard.P1),7);
		assertEquals("counting P2",board.getCount(OthelloBoard.P2),2);
	}
}
