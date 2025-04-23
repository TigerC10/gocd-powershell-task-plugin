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

if ! grep -q 'agentAutoRegisterKey="agent-autoregister-key"' /go-working-dir/config/cruise-config.xml; then
  echo 'Agent auto registration key is already set up.'
  exit 0
fi

uuid=$(python -c 'import uuid; print str(uuid.uuid4())')

sed -i -e "s/agentAutoRegisterKey=\"agent-autoregister-key\"/agentAutoRegisterKey=\"${uuid}\"/" /go-working-dir/config/cruise-config.xml

echo -n "${uuid}" >/shared/autoregister.key
