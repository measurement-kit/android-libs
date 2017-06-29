// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

%module mk_swig_ooni
%include "std_string.i"
%include "stdint.i"

%typemap(jstype) mk_array_list_strings_t "java.util.ArrayList<String>"
%typemap(jtype) mk_array_list_strings_t "java.util.ArrayList<String>"
%typemap(javain) mk_array_list_strings_t "$javainput"

%typemap(jstype) mk_list_strings_t "java.util.List<String>"
%typemap(jtype) mk_list_strings_t "java.util.List<String>"

%typemap(jstype) mk_error_t "Error"
%typemap(jtype) mk_error_t "Error"

%typemap(jstype) mk_orchestrate_register_probe_callback_t "OrchestrateRegisterProbeCallback"
%typemap(javain) mk_orchestrate_register_probe_callback_t "$javainput"

%typemap(jstype) mk_orchestrate_find_location_callback_t "OrchestrateFindLocationCallback"
%typemap(javain) mk_orchestrate_find_location_callback_t "$javainput"

%typemap(jstype) mk_orchestrate_update_callback_t "OrchestrateUpdateCallback"
%typemap(javain) mk_orchestrate_update_callback_t "$javainput"

%{

#include "ooni.hpp"

%}

%include "ooni.hpp"
