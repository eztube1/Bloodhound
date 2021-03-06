/*
 *   Copyright 2019 API Fortress
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   @author Simone Pezzano
 *
 */

package com.apifortress.afthem.actors.filters

import com.apifortress.afthem.actors.AbstractAfthemActor
import com.apifortress.afthem.exceptions.AfthemFlowException
import com.apifortress.afthem.messages.BaseMessage

import scala.concurrent.duration.Duration

/**
  * Actor delaying a request
  *
  * @param phaseId phase ID
  */
class DelayActor(phaseId : String) extends AbstractAfthemActor(phaseId : String) {

  override def receive: Receive = {
    case msg : BaseMessage =>
      try {
        val delay = getPhase(msg).getConfigString("delay")
        Thread.sleep(Duration.create(delay).toMillis)
        forward(msg)
      }catch {
        case e : Exception => throw new AfthemFlowException(msg,e.getMessage)
      }
  }

}
