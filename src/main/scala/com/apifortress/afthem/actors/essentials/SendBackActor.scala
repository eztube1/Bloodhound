/**
  * Copyright 2019 API Fortress
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  *
  * @author Simone Pezzano
  */
package com.apifortress.afthem.actors.essentials

import com.apifortress.afthem.actors.AbstractAfthemActor
import com.apifortress.afthem.messages.WebParsedResponseMessage
import org.springframework.http.ResponseEntity

class SendBackActor(phaseId: String) extends AbstractAfthemActor(phaseId: String) {



  override def receive: Receive = {
    case msg: WebParsedResponseMessage => {
      val response = msg.response
      var envelopeBuilder = ResponseEntity.status(response.status)
      response.headers.foreach( header=> envelopeBuilder=envelopeBuilder.header(header._1,header._2))
      val bodyBuilder = envelopeBuilder.body(response.payload)
      msg.deferredResult.setResult(bodyBuilder)
    }
  }

}
