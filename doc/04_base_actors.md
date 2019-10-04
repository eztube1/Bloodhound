# AFtheM - base actors

## Type: Proxy

### RequestActor

This is a special actor and needs to be instantiated with the special id **`proxy/request`**

**class:**  `com.apifortress.afthem.actors.proxy.RequestActor`

**sidecars:** yes

**config:**

* `discard_headers`: a list of request header names that need to be discarded immediately

### UpstreamHttpActor

The default upstream actor.

**class:** `com.apifortress.afthem.actors.proxy.UpstreamHttpActor`

**sidcars:** yes

**config:**

* `connect_timeout`: timeout for the connection process in milliseconds
* `socket_timeout`: timeout for silent socket in milliseconds
* `redirects_enabled`: set to true if you want AFtheM to resolve redirects instead of forwarding to the client
* `max_redirects`: if redirects are enabled, maximum number of redirects before giving up 
* `discard_headers`: a list of response header names that need to be discarded immediately


### UpstreamFileActor

An upstream actor that pulls content from text files.

**class:** `com.apifortress.afthem.actors.proxy.UpstreamFileActor`

**sidecars:** yes

**config:**

* `basepath`: path to the files directory. The file name needs to be provided in the request url

### SendBackActor

An actor taking care of performing the final checks, packaging and sending back the content.

**class:** `com.apifortress.afthem.actors.proxy.SendBackActor`

**sidecars:** yes

****

## Type: Sidecar

### AccessLoggerActor

A logger actor meant to log both inbound and outbound calls.
The behavior of the logging activity is managed by the `etc/logback.xml` file.

**class:** `com.apifortress.afthem.actors.sidecars.AccessLoggerActor`

**sidecars:** no


### GenericLoggerActor

A logger to be used in a flow to log certain facts, determined by the user.
The behavior of the logging activity is managed by the `etc/logback.xml` file.

**class:** `com.apifortress.afthem.actors.sidecars.GenericLoggerActor`

**sidecars:** no

**config:**

* `value`: the content to be logged

* `evaluated`: if set to true, the `value` field will be interpreted as a SpEL script. The message is accessible via the
  `msg` variable.

### FileAppenderSerializerActor

Serializes a full API conversation to JSON and appends it to a file.

**class:** `com.apifortress.afthem.actors.sidecars.serializers.FileAppenderSerializerActor`

**sidecars:** no

**config:**

* `filename`: name of the file
* `disable_on_header`: if the provided header is present in the request, then the conversation will skip serialization
* `enable_on_header`: if the provided header is present in the request, then the conversation will be serialized
* `discard_request_headers`: list of request headers that should not appear in the serialized conversation
* `discard_response_headers`: list of response headers that should not appear in the serialized conversation
* `allow_content_types`: full or partial response content types which make the request eligible for serialization. If
the list is null or empty, all content types will be accepted

***

## Type: Transformer

### TransformHeadersActor

Alters the headers of a message. If the transformer is placed before an Upstream actor, it modifies the request headers.
If after, it modifies the response headers.

**class:** `com.apifortress.afthem.actors.transformers.TransformHeadersActor`

**config:**

* `add`: adds a header. If `evaluated` is set to `ŧrue`, the value is treated as a SpEL script. Example:
  ```yaml
    add:
      - name: header_name
        value: header_value
        evaluated: false
  
   ```
* `remove`: removes a header. Example:
  ```yaml
    remove:
      - name: header_name
  
  ```
* `set`: sets the value of an existing header, or adds it if the header is not present. If `evaluated` is set to `ŧrue`,
   the value is treated as a SpEL script. Example:
   ```yaml
   set:
     - name: header_name
       value: header_value
       evaluated: false
     
   ```
 
### TransformPayloadHeader

Alters a textual payload in a message. If the transformer is placed before an Upstream actor, it modifies the request
payload. If after, it modifies the response payload.

**class:** `com.apifortress.afthem.actors.transformers.TransformPayloadActor`

**sidecars:** yes

**config:**

* `set`: sets the payload with the given value
* `replace`: replaces all the substrings matching a certain regular expression with the provided string. Example:
  ```yaml
    replace:
      regex: foo
      value: bar
  ```

### ReplaceUpstreamActor

Replaces the upstream base URL if a certain condition is verified.

**class:** `com.apifortress.afthem.actors.transformers.ReplaceUpstreamActor`

**sidecars:** yes

**config:**

* `expression`: a SpEL expression returning a boolean. The condition to be matched
* `upstream`: the new upstream base URL

***

## Type: Filter

### FilterActor

Filters out any request not matching a certain set of criteria.

**class:** `com.apifortress.afthem.actors.filters.FilterActor`

**sidecars:** yes

**config:**

* `accept`: a list of conditions. If verified, the message will be accepted.
   Example:
   ```yaml
    accept:
      - value: "#msg.request().getHeader('accept')=='application/json'"
        evaluated: true
      - value: "#msg.request().getHeader('key')=='ABC123'"
        evaluated: true  
   ```
   Just like previous filters, if evaluated is true,`value` will be evaluated as SpEL script.
* `reject`: a list of conditions. If verified, the message will be rejected.
  Example:
  ```yaml
  reject:
    - value: "#msg.request().method()!='GET'"
      evaluated: true
  ```

### ApiKeyFilterActor

Filters out any request that does not carry a valid API key in the headers or in the query string.
This base actor loads the API keys from a YAML file.

**class:** com.apifortress.afthem.actors.filters.ApiKeyFilterActor

**sidecars**: yes

**config:**

* `filename`: path to a file containing the API keys
* `in`: either `query` (expecting the key in the query string) or `header` (expecting the key in the headers)
* `name`: key of the field carrying the API key