/*==============================================================================
FMOD Example Framework
Copyright (c), Firelight Technologies Pty, Ltd 2013-2023.
==============================================================================*/
#include <pthread.h>
#include <assert.h>
#include <stdio.h>
bool Common_SuspendState();

#define Common_TTY(format, ...) fprintf(stderr, format, __VA_ARGS__)

