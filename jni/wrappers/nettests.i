// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

%module mk_swig_nettests
%include "std_string.i"
%include "stdint.i"

%typemap(jstype) mk_entry_callback_t "EntryCallback"
%typemap(javain) mk_entry_callback_t "$javainput"

%typemap(jstype) mk_event_callback_t "EventCallback"
%typemap(javain) mk_event_callback_t "$javainput"

%typemap(jstype) mk_log_callback_t "LogCallback"
%typemap(javain) mk_log_callback_t "$javainput"

%typemap(jstype) mk_progress_callback_t "ProgressCallback"
%typemap(javain) mk_progress_callback_t "$javainput"

%typemap(jstype) mk_test_complete_callback_t "TestCompleteCallback"
%typemap(javain) mk_test_complete_callback_t "$javainput"

%{

#include "nettests.hpp"

%}

%include "nettests.hpp"
