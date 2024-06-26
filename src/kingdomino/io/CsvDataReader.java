package kingdomino.io;

import java.io.*;
import java.util.*;

import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

public final class CsvDataReader {
	public static List<DominoDTO> readDominoesFromFile(String filePath) {
		List<DominoDTO> result = new ArrayList<>();

		try (var csvReader = new CsvMapReader(new FileReader(filePath), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE)) {
			csvReader.getHeader(true);
			Map<String, String> line = csvReader.read("CrownsCount1", "Terrain1", "CrownsCount2", "Terrain2", "Id");

			while (line != null) {
				result.add(new DominoDTO(Integer.parseInt(line.get("Id")), line.get("Terrain1"),
						Integer.parseInt(line.get("CrownsCount1")), line.get("Terrain2"),
						Integer.parseInt(line.get("CrownsCount2"))));
				line = csvReader.read("CrownsCount1", "Terrain1", "CrownsCount2", "Terrain2", "Id");
			}
			
			return result;
		} catch(Exception ex) {
			throw new RuntimeException("Unable to load dominoes", ex);
		}
	}
	
	public static List<PlayerDTO> readPlayersFromFile(String filePath) {
		List<PlayerDTO> result = new ArrayList<>();

		try (var csvReader = new CsvMapReader(new FileReader(filePath), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE)) {
			csvReader.getHeader(true);
			Map<String, String> line = csvReader.read("Id", "HexColor");

			while (line != null) {
				result.add(new PlayerDTO(line.get("Id"), line.get("HexColor")));
				line = csvReader.read("Id", "HexColor");
			}
			
			return result;
		} catch(Exception ex) {
			throw new RuntimeException("Unable to load players", ex);
		}
	}
}
