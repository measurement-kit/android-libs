// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

%module(directors="1") Api

%{
#include <measurement_kit/nettest.hpp>
%}

%include "std_string.i"
%include "stdint.i"
%include "stl.i"

%feature("director");

%ignore mk::nettest::common::Nettest::dispatch_event;
%ignore mk::nettest::common::Settings::annotations;
%ignore mk::nettest::common::NeedsInputSettings::inputs;
%ignore mk::nettest::common::NeedsInputSettings::input_filepaths;

%extend mk::nettest::common::Settings {
  void addAnnotation(std::string key, std::string value) {
    std::swap($self->annotations[key], value);
  }
};

%extend mk::nettest::common::NeedsInputSettings {
  void addInput(std::string value) {
    $self->inputs.push_back(std::move(value));
  }
  void addInputFilepath(std::string value) {
    $self->input_filepaths.push_back(std::move(value));
  }
};

%rename("%(lowercamelcase)s", %$isfunction) "";
%rename("%(lowercamelcase)s", %$isvariable) "";
%rename("%(regex:/([A-Za-z]+)(.*)Event/Event\\1/)s") "";
%rename("%(regex:/([A-Za-z]+)(.*)Nettest/Nettest\\1/)s") "";
%rename("%(regex:/([A-Za-z]+)(.*)Settings/Settings\\1/)s") "";
%rename("%(regex:/^Settings$/SettingsBase/)s") "";
%rename("%(regex:/^Nettest$/NettestBase/)s") "";
%rename("%(regex:/^PerformanceNettest$/NettestBasePerformance/)s") "";
%rename("%(regex:/^WebsitesNettest$/NettestBaseWebsites/)s") "";
%rename("%(regex:/^NeedsInputSettings$/SettingsBaseNeedsInput/)s") "";

%include <measurement_kit/nettest.hpp>
