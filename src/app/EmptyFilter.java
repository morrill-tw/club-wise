package app;

import java.util.List;

public class EmptyFilter implements Filter {
  @Override
  public List<Club> filter(List<Club> clubs) {
    return clubs;
  }
}
