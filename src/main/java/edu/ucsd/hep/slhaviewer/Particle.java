/*
 * Copyright 2015 University of California, San Diego.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.ucsd.hep.slhaviewer;

/**
 * contains information about one particle
 * @author holzner
 */
public class Particle
{
  public final int pdgId;
  
  public final String shortName;

  public final String verboseName;
  
  public Particle(int pdgId, String shortName, String verboseName)
  {
    this.pdgId = pdgId;
    this.shortName = shortName;
    this.verboseName = verboseName;
  }

  @Override
  public String toString()
  {
    return shortName;
  }

  
}
