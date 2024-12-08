package app;

import java.util.ArrayList;
import java.util.List;

public class ActiveFilter implements Filter {
  @Override
  public List<Club> filter(List<Club> clubs) {
    List<Club> filtered = new ArrayList<Club>();
    for (Club club : clubs) {
      if (club.getActiveStatus()) {
        filtered.add(club);
      }
    }
    return filtered;
  }
}
