import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Solution {

	static final String filename = "full.in";

	public static void main(String[] args) {
		BufferedReader reader;
		List<Group> groups = new ArrayList<>();

		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				var a = new Rucksack(line);
				var b = new Rucksack(reader.readLine());
				var c = new Rucksack(reader.readLine());
				groups.add(new Group(a,b,c));
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		var r =  groups
			.stream()
			.map(group -> group.badge().priority())
			.reduce(0, Integer::sum);

		System.out.println(r);

	}
}

class Group {

	Rucksack a;
	Rucksack b;
	Rucksack c;

	public Group(Rucksack a,Rucksack b,Rucksack c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Item badge() {
		var intersect = a.contents();
		intersect.retainAll(b.contents());
		intersect.retainAll(c.contents());
		Object[] objs = intersect.toArray();
		return (Item) objs[0];
	}

}

class Rucksack {
	final Set<Item> contents;

	public Rucksack(String src) {
		contents = new HashSet<>();
		for (char c : src.toCharArray()) {
			contents.add(new Item(c));
		}
	}

	public Set<Item> contents() {
		return contents;
	}


}


class Item {
	private char me;

	public Item(char c) {
		me = c;
	}

	public int priority() {
		var ord = (int) me;
		if (ord >= 0x41 && ord < 0x5B) {
			return (ord - 0x41) + 27;
		} else if (ord >= 0x61 && ord <= 0x7b) {
			return ord - 0x60;
		} else {
			throw new RuntimeException("Invalid Item: " + this);
		}
	}

	public String toString() {
		return String.format("%c", me);
	}


	public boolean equals(Object o) {
		return (o instanceof Item) && ((Item) o).me == this.me;
	}

	public int hashCode() {
		return (int) me;
	}

}

