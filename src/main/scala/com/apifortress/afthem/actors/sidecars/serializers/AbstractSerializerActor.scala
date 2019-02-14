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

package com.apifortress.afthem.actors.sidecars.serializers

import java.nio.charset.StandardCharsets

import com.apifortress.afthem.{Parsers, UriUtil}
import com.apifortress.afthem.actors.AbstractAfthemActor
import com.apifortress.afthem.messages.WebParsedResponseMessage

import scala.collection.mutable

abstract class AbstractSerializerActor(phaseId : String) extends AbstractAfthemActor(phaseId : String) {

  def serialize(message: WebParsedResponseMessage): String = {
    val obj = mutable.HashMap.empty[String,Any]
    obj.put("client_ip",message.request.remoteIP)
    obj.put("started_at",message.date.getTime)

    val request = mutable.HashMap.empty[String,Any]
    request.put("body",new String(message.request.payload,StandardCharsets.UTF_8))
    request.put("size",message.request.payload.size)

    request.put("uri",UriUtil.toSerializerUri(message.request.url))
    request.put("request_uri",message.request.url)
    request.put("method",message.request.method)
    val requestHeaders = mutable.HashMap.empty[String,String]
    message.request.headers.foreach(header => requestHeaders.put(header._1,header._2))
    request.put("headers",requestHeaders)
    obj.put("request",request)

    val response = mutable.HashMap.empty[String,Any]
    response.put("body",new String(message.response.payload,StandardCharsets.UTF_8))
    response.put("size",message.response.payload.size)
    response.put("status",message.response.status)
    val responseHeaders = mutable.HashMap.empty[String,String]
    message.response.headers.foreach(header => responseHeaders.put(header._1,header._2))
    request.put("headers",responseHeaders)
    obj.put("response",response)

    return Parsers.deserializeAsJsonString(obj,false)

  }

}