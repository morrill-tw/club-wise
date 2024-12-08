package app;

import java.util.List;

public class CategoryFilter implements Filter {
  private final String category;

  public CategoryFilter(String category) {
    this.category = category;
  }

  @Override
  public List<Club> filter(List<Club> clubs) {
    return null;
  }
}
