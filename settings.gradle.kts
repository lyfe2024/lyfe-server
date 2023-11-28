rootProject.name = "lyfe-server"
include(
    "core:lyfe-core",
    "infrastructure:datastore-mysql",
    "infrastructure:aws",
    "controller:lyfe-api",
    "util:common-util",
)
