db.createUser({
        user: 'root',
        pwd: 'toor',
        roles: [
            {
                role: 'readWrite',
                db: 'testDB',
            },
        ],
        user: 'master',
        pwd: 'mongo',
        roles: [
            {
                role: 'readWrite',
                db: 'users',
            },
        ]
    });
db.createCollection('app_users', { capped: false });

db.app_users.insert([
    {
        "username": "ragnar777",
        "dni": "VIKI771012HMCRG093",
        "enabled": true,
        "password" : "$2a$10$nUN8YRXXKLcWYG9r5cXjy..7ZlkQn742z0L0YS3HSIpelCqjcoeei",
        "password_not_encrypted": "s3cr3t",
        "role":
        {
            "granted_authorities": ["ROLE_USER"]
        }
    },
    {
        "username": "heisenberg",
        "dni": "BBMB771012HMCRR022",
        "enabled": true,
        "password_not_encrypted": "p4sw0rd",
        "password": "$2a$10$C8WFLGH6IYeNzF4iKCJ4AOkMN2O2yqRyiBTXtE71uvBAlpNxazjla",
        "role":
        {
            "granted_authorities": ["ROLE_USER"]
        }
    },
    {
        "username": "misterX",
        "dni": "GOTW771012HMRGR087",
        "enabled": true,
        "password_not_encrypted": "misterX123",
        "password": "$2a$10$KdKoIxY3KCELEblvpwWxsufv4Enf0arrYMKaXHVUEpb5BXLLCFfnm",
        "role":
        {
            "granted_authorities": ["ROLE_USER", "ROLE_ADMIN"]
        }
    },
    {
        "username": "neverMore",
        "dni": "WALA771012HCRGR054",
        "enabled": true,
        "password_not_encrypted": "4dmIn",
        "password":"$2a$10$SR3ArTcL/SSEpUJT/QuOpuWp8PCmt4VVGgWidLCJwI0JPUnt5qyEe",
        "role":
        {
            "granted_authorities": ["ROLE_ADMIN"]
        }
    }
]);