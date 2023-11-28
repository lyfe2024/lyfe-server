rootProject.name = "lyfe-server"
include(
    "model",
    "usecase",
    "infrastructure:datastore-mysql",
    "infrastructure:aws",
    "controller:lyfe-api",
    "util:common-util",
)
