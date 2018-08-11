// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

%module(directors="1") mk_swig_nettest

%{
#include <measurement_kit/nettest.hpp>
%}

%include "std_string.i"
%include "stdint.i"
%include "stl.i"

%feature("director");

%ignore mk::nettest::Settings::annotations;
%ignore mk::nettest::Settings::inputs;
%ignore mk::nettest::Settings::input_filepaths;

%extend mk::nettest::Settings {
  void addAnnotation(std::string key, std::string value) {
    std::swap($self->annotations[key], value);
  }
  void addInput(std::string value) {
    $self->inputs.push_back(std::move(value));
  }
  void addInputFilepath(std::string value) {
    $self->input_filepaths.push_back(std::move(value));
  }
};

%rename("%(lowercamelcase)s", %$isfunction) "";
%rename("%(lowercamelcase)s", %$isvariable) "";

%include <measurement_kit/nettest.hpp>
