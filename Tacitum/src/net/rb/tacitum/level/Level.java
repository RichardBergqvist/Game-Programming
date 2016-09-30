package net.rb.tacitum.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.rb.tacitum.entity.Entity;
import net.rb.tacitum.entity.mob.Mob;
import net.rb.tacitum.entity.mob.player.Player;
import net.rb.tacitum.entity.particle.Particle;
import net.rb.tacitum.entity.projectile.Projectile;
import net.rb.tacitum.entity.spawner.Spawner;
import net.rb.tacitum.events.Event;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.layers.Layer;
import net.rb.tacitum.level.tile.Tile;
import net.rb.tacitum.util.Vector2i;

/**
 * @author Richard Bergqvist
 * @since In-Development 3.0
 * @category Mapping
 * **/
public class Level extends Layer {
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	
	private int xScroll, yScroll;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	
	public List<Mob> players = new ArrayList<Mob>();
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	public static final Level SPAWN = new SpawnLevel("/levels/spawn.png");
	public static final Level MONX = new SpawnLevel("/levels/monx.png");
	public static final Level SPACE = new SpaceLevel("/levels/space.png");
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		this.tilesInt = new int[width * height];
		
		generateLevel();
	}
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	protected void generateLevel() {}
	
	protected void loadLevel(String path) {}
	
	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();;
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();;
		}
		
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();;
		}
		
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();;
		}
		
		remove();
	}
	
	public void onEvent(Event event) {
		this.getClientPlayer().onEvent(event);
	}
	
	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
		}
	}
	
	protected void time() {
		
	}
	
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).isSolid()) solid = true;
		}
		return solid;
	}
	
	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}
	
	public void render(Screen screen) {
		screen.setOffset(xScroll, yScroll);
		
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}
	
	public void add(Entity e) {
		e.init(this);
		
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
		 	players.add((Player) e); 
		} else {
			entities.add(e);
		}
	}
	
	public void addPlayer(Mob player) {
		player.init(this);
		players.add(player);
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public List<Mob> getPlayers() {
		return players;
	}
	
	public Mob getPlayerAt(int index) {
		return players.get(index);
	}
	
	public Player getClientPlayer() {
		return (Player) players.get(0);
	}
	
	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null) continue;
				if (at.isSolid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (VectorInList(closedList, a) && gCost >= node.gCost) continue;
				if (!VectorInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}
	
	private boolean VectorInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}
	
	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = e.getX();
		int ey = e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity instanceof Projectile || entity instanceof Particle || entity instanceof Spawner) break;
			if (entity.equals(e)) continue;
 			int x = entity.getX();
			int y = entity.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) result.add(entity);
		}
		return result;
	}
	
	public List<Mob> getPlayers(Entity e, int radius) {
		List<Mob> result = new ArrayList<Mob>();
		int ex = e.getX();
		int ey = e.getY();	
		for (int i = 0; i < players.size(); i++) {
			Mob player = players.get(i);
			int x = player.getX();
			int y = player.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);		
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) result.add(player);
		}	
		return result;
	}
	
	private Tile void_tile;
	public Tile getTile(int x, int y) {	
		if (this == SPACE) {
			if (x < 0 || y < 0 || x >= width || y >= height) return Tile.space_tile;
			if (tiles[x + y * width] == SpaceLevel.col_space_wall) return Tile.space_wall;
			if (tiles[x + y * width] == SpaceLevel.col_space_wall1) return Tile.space_wall1;
			if (tiles[x + y * width] == SpaceLevel.col_space_star) return Tile.space_star;
			if (tiles[x + y * width] == SpaceLevel.col_space_floor) return Tile.space_floor;
			void_tile = Tile.space_tile;
		}
		
		if (this == SPAWN) {
			if (x < 0 || y < 0 || x >= width || y >= height) return Tile.void_tile;
			if (tiles[x + y * width] == SpawnLevel.col_spawn_grass) return Tile.spawn_grass_tile;
			//if (tiles[x + y * width] == Tile.col_spawn_hedge) return Tile.spawn_hedge_tile;
			if (tiles[x + y * width] == SpawnLevel.col_spawn_water) return Tile.spawn_water_tile;
			if (tiles[x + y * width] == SpawnLevel.col_spawn_wall1) return Tile.spawn_wall1_tile;
			if (tiles[x + y * width] == SpawnLevel.col_spawn_wall2) return Tile.spawn_wall2_tile;
			if (tiles[x + y * width] == SpawnLevel.col_spawn_wall3) return Tile.spawn_wall3_tile;
			if (tiles[x + y * width] == SpawnLevel.col_spawn_wall4) return Tile.spawn_wall4_tile;
			if (tiles[x + y * width] == SpawnLevel.col_spawn_floor) return Tile.spawn_floor_tile;
			void_tile = Tile.void_tile;
		}
		return void_tile;
	}
}