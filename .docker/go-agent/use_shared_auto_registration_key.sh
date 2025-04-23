#!/bin/bash

#
# Copyright 2025 Volusion, LLC.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

set -Eeuo pipefail

while [ ! -f '/shared/autoregister.key' ]; do
  echo 'Waiting for server to decide agent auto registration key ...'
  sleep 3
done

uuid=$(cat '/shared/autoregister.key')

sed -i -e "s/agent.auto.register.key=.*/agent.auto.register.key=${uuid}/" '/go/config/autoregister.properties'
