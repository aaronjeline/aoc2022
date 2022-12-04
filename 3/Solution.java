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
		List<Rucksack> rucksacks = new ArrayList<>();

		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				rucksacks.add(new Rucksack(line));
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		var r = rucksacks
			.stream()
			.map(rucksack -> rucksack.overlapPriority())
			.reduce(0, Integer::sum);

		System.out.println("Answer: " + r);


	}
}

class Rucksack {
	final Set<Item> first;
	final Set<Item> second;

	public Rucksack(String contents) {

		this.first = new HashSet<>();
		this.second = new HashSet<>();

		var split = contents.length() / 2;

		for (char c : contents.substring(0, split).toCharArray()) {
			first.add(new Item(c));
		}
		for (char c : contents.substring(split, contents.length()).toCharArray()) {
			second.add(new Item(c));
		}

	}

	public int overlapPriority() {
		Set<Item> intersect = new HashSet<>(first);
		intersect.retainAll(second);
		Object[] objs = intersect.toArray();
		var duplicate = (Item) objs[0];
		return duplicate.priority();
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

