rootProject.name = "lyfe-server"
include(
    "core:lyfe-core",
    "infrastructure:datastore-mysql",
    "infrastructure:aws",
    "server:lyfe-api",
    "util:common-util",
)
