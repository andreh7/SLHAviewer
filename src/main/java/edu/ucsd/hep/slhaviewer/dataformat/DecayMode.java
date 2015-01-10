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
package edu.ucsd.hep.slhaviewer.dataformat;

import edu.ucsd.hep.slhaviewer.Particle;
import java.util.List;

/** class representing a decay mode of a particle */
public class DecayMode
{
  public final double branchingRatio;
  public final List<Particle> daughters;

  public DecayMode(double branchingRatio, List<Particle> daughters)
  {
    this.branchingRatio = branchingRatio;
    this.daughters = daughters;
  }
  
}
