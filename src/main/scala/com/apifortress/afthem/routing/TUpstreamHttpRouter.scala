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
package com.apifortress.afthem.routing

import com.apifortress.afthem.config.Backend

/**
  * A trait for all the upstream http routers
  */
trait TUpstreamHttpRouter {

  var backendHashCode : Int = -1

  var urls : List[String] = null

  /**
    * Computes and returns the next upstream url that needs to be used
    * @return the next upstream url that needs to be used
    */
  def getNextUrl() : String

  /**
    * Updates the router based on the provided backend
    * @param backend a Backend instance
    */
  def update(backend: Backend) : Unit

}
