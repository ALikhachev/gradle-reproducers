package org.example;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MyPlugin implements Plugin<Project> {

  @Override
  public void apply(Project target) {
    System.out.println("Apply plugin");
  }
}
