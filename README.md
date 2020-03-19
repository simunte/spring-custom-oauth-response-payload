# spring-custom-oauth-response-payload
merupakan project untuk custom response payload (token maupun body) dari oauth spring security


#Prerequisites

JDK 1.8 Or Later
MySQL
Editor or IDE (Eclipse, IntelliJ, etc)

1. default response payload :
    {
      "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXcvYWRtaW5hcHAiXSwidXNlcl9uYW1lIjoidXNlcjEiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTg0NjE3MjQ2LCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiI0MGRiNGY5OS0wMWFjLTQ1ZDctOTliMi1lNzc2MTBiMDZiNmEiLCJjbGllbnRfaWQiOiJhZG1pbmFwcCJ9.YatodsXi3dg5SXqUfrUMl8uK3QivoZbUAfuMTwzOe9zVuTrRIce5Z76kwAAX6bl7FQsfzWjjaMluJGZS_xS05OFJRoyU9IPCpVJ0xKD4_pbsSauYhCRUAtSwHKB-XdtCQvFd8USrh-4lDLXX0pC6Tl2b317wu9Ozbxdnz_rXjetiOgDfr0yPEdqJ3gUij08M-zdFK1OCwqUQmMcW0bBFg8eE0bS97PjWovJtJLiJm_3L-woQdwvGqkgBn28hd9st373z8-kVzrCQrX31a7FgtNlhMVXANEsm8Pt8tkTy5lOWeZBVyfWRl_nucOmD12lWzX01LYo14BdqIQpJVSpkTA",
      "token_type": "bearer",
      "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXcvYWRtaW5hcHAiXSwidXNlcl9uYW1lIjoidXNlcjEiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXRpIjoiNDBkYjRmOTktMDFhYy00NWQ3LTk5YjItZTc3NjEwYjA2YjZhIiwiZXhwIjoxNTg0NjM1MjQ2LCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiJlMzE0ZWM4Ny03YWQ3LTQyMmYtYWU0MS1iOWJkNzkwNTVhM2UiLCJjbGllbnRfaWQiOiJhZG1pbmFwcCJ9.Y0k5p7lX7_OlJUwTujrVgmmgTkRz0wMh4oPDQdZkJFd9Ll9J5RS_UeQZ1keFBOu--qI4eN3JmlBk18b_ggQmxwnhtMpxYevbXBBEEvIjUUAEpabVbb7IVn5qjLhVOWS103UGjJerNvwCHu1FZmojm2eATJ0YGXgyKolGdU6LuxWf8qFejwUIdqk56i-eL2NBC5natAIkTd4Ar2ng3mDyb-v3ymikG8GxDRpyusgGUiv_T0poTY-RGYmpuOx7BI5xNZlIxymbzCJVyXKM7P9jruyNqWR9POrfmSMDPtRf1nKa0QoPMrASB_GGzuWh72STQNfTGYECEk-uJo5XL1-GTQ",
      "expires_in": 3599,
      "scope": "read write",
      "jti": "40db4f99-01ac-45d7-99b2-e77610b06b6a"
    }
    if you convert acess_token using jwt.io online converter, then the result :
    "aud": [
        "mw/adminapp"
      ],
      "user_name": "user1",
      "scope": [
        "read",
        "write"
      ],
      "exp": 1584617246,
      "authorities": [
        "ADMIN"
      ],
      "jti": "40db4f99-01ac-45d7-99b2-e77610b06b6a",
      "client_id": "adminapp"
    }

2. Custom (add payload inside access_token):
   For this part you only need to change the method GrantedAuthority method that you already override in your User domain.
   {
     "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXcvYWRtaW5hcHAiXSwidXNlcl9uYW1lIjoidXNlcjEiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTg0NjE3ODYyLCJhdXRob3JpdGllcyI6WyJBRE1JTklTVFJBVE9SIiwiU1VQRVJWSVNPUiJdLCJqdGkiOiI5ZWYyY2U3Ny03OTExLTRjODEtOWM4Yi0yYTRmMTVkOTU5ZTUiLCJjbGllbnRfaWQiOiJhZG1pbmFwcCJ9.Dc5DNZfFyXAJkLFV6JtlEVvZrUzcMFCOq7X_T0YhN1qAhAu7UgROu0h3x-aTqfIwvSLdSrwcdW4s6ObF_s--p7jWzcYa5ew1gOMV3ql39QZJDkcb-4ZclHN69YOrt_G-OxN_BRFQ30nukdf7IUmOgxKkeWPI1ZGv3KC28vp5Z21Ehptl6AUGS6CMQQYj6UkK9Aq2mdrcZZw4uhamPUvMyY9rl9MhrL9ONctcJOIAI6UFM2iNBJ2_VkVi48VQ4gvxmwho3zRi2gFo9zCXU5YLUn1aDDJVNf-FNvv73dNeco0xbVx3yHdR1sUWRSuwuUUOHymaP7lWv5AWHX4XSsygDw",
     "token_type": "bearer",
     "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXcvYWRtaW5hcHAiXSwidXNlcl9uYW1lIjoidXNlcjEiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXRpIjoiOWVmMmNlNzctNzkxMS00YzgxLTljOGItMmE0ZjE1ZDk1OWU1IiwiZXhwIjoxNTg0NjM1ODYyLCJhdXRob3JpdGllcyI6WyJBRE1JTklTVFJBVE9SIiwiU1VQRVJWSVNPUiJdLCJqdGkiOiI5MzhkM2I3Yy00OTdkLTQ5NDktYjVlZC1kYjFhNWM3YzczNjkiLCJjbGllbnRfaWQiOiJhZG1pbmFwcCJ9.DI7nPnT9t35s_IOJ4rjtr1V1UwycBTBnMphS4qcAHDmbMNetVICu847Go9oZx2kHM5160bevezzUJOV770h96toa0GANGBGkmCc9FMGJ7pzhgbBeJTFj3vunvdvFXN5GO8UI9Z1T1ttbjQwSVPtxzkXzhSzSY0koVGc1ThCqqfVRTmm1b8qpSOZN5OcH7RvHMwahy6ZZ7CnmVmCpOO9zC3WPOymgpZ82qZqFT1Dw9K6E1gceeYfMjSmbZMhwfLBjcPeS5ayrQ3-sYhFHmA56UVoIs1oKmnyrFevAK-T4VIqt94dc05c5n497dfcNIckoYuPQYYB3vy7qvqfqcBBV2Q",
     "expires_in": 3599,
     "scope": "read write",
     "jti": "9ef2ce77-7911-4c81-9c8b-2a4f15d959e5"
   }

   if you convert acess_token using jwt.io online converter, then the result :
   {
     "aud": [
       "mw/adminapp"
     ],
     "user_name": "user1",
     "scope": [
       "read",
       "write"
     ],
     "exp": 1584617862,
     "authorities": [
       "ADMINISTRATOR",
       "SUPERVISOR"
     ],
     "jti": "9ef2ce77-7911-4c81-9c8b-2a4f15d959e5",
     "client_id": "adminapp"
   }
3. Custom (add new field):
   for this part you need to modify your web security Configuration
   below new payload of your oauth/token api (there is a "custom_payload" field added):
    {
      "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXcvYWRtaW5hcHAiXSwidXNlcl9uYW1lIjoidXNlcjEiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTg0NjE5MTMyLCJhdXRob3JpdGllcyI6WyJBRE1JTklTVFJBVE9SIiwiU1VQRVJWSVNPUiJdLCJqdGkiOiJhNTE4NjFjYi05NGY5LTQzY2YtYjYzYi02MTZiMmRjMmUxNmQiLCJjbGllbnRfaWQiOiJhZG1pbmFwcCJ9.VBx52Qp_ehJsbXToNHstXRyrHaaUc0SxtIB8PRCLSFvGRomuGFUEd3KQrxxv_4tDcapGlmrc5mmvwItXfdXuyRKEvbUjoKmctLFF_CZ8IzzmvY_4hDQJCX2ENrw9YBAsXqFW1qTpAoIATFkwie5ryMqRf0FGaiRCmJyCtTe-tqC4MNaaKw40hfmBzpC5AKno2HcV9bRK0esp9d3zC4KV36y4LFuaKyAGq_FCICjb2zUkm1yIb_qvNU7qJ9Itsp1B2qLaevGlkRgDoK9I0mlXViljTdm5v_j9WSxEgj9V7aZSEiwZIMN9Rz3zNL1_VZPPJ3RWV7HLth-ERjJ-3Yp9uQ",
      "token_type": "bearer",
      "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXcvYWRtaW5hcHAiXSwidXNlcl9uYW1lIjoidXNlcjEiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXRpIjoiYTUxODYxY2ItOTRmOS00M2NmLWI2M2ItNjE2YjJkYzJlMTZkIiwiZXhwIjoxNTg0NjM3MTMyLCJhdXRob3JpdGllcyI6WyJBRE1JTklTVFJBVE9SIiwiU1VQRVJWSVNPUiJdLCJqdGkiOiI5NThkNTkwOC02OTMwLTQzZjktOWMxZS1mOGNhNDM1ZmE5N2IiLCJjbGllbnRfaWQiOiJhZG1pbmFwcCJ9.JztRJ0OY70NchAvKApGLdjFA3I6wxQJERj7G7tEiJLf_Mut2N69AQ0SI-zB-bUxj-jxajuBEE3FX0S1oj_AnThkNfENzq-x4BPHfbrTrEFtt9riCOHw0bzvVwRF0ZmpfNHtRreA9cJ-W85wvK9ZXEEuy77PCVNUlDwLlQ3uj1jsr5IqN_TKKiylp71aClgDBfw7aMSW5OTkka-ZI9Rzvq_FWqkoxfqtPw8PnprrPEd_hNcJAtFtgb9WA0Rh2DPavP0cxc4khphEx8w1dDYda89fv_VLg6EA6w2igYKBcdnbqoCr4pc8UhC7zWjRkLCNC_jJPHthOsD7nZGtdGuuymA",
      "expires_in": 3599,
      "scope": "read write",
      "custom_payload": {
        "username": "user1",
        "email": "user1@mailinator.com",
        "list_role": [
          {
            "role_id": 1,
            "role_name": "ADMINISTRATOR",
            "role_description": "ADMINISTRATOR"
          },
          {
            "role_id": 3,
            "role_name": "SUPERVISOR",
            "role_description": "SUPERVISOR"
          }
        ]
      }
    }